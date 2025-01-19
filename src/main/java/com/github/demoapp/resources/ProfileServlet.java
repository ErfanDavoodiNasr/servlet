package com.github.demoapp.resources;


import com.github.demoapp.exception.FileIsBigException;
import com.github.demoapp.exception.NotUniqueEmailException;
import com.github.demoapp.exception.PasswordDoesntMatchException;
import com.github.demoapp.exception.PasswordIsIncorrectException;
import com.github.demoapp.model.User;
import com.github.demoapp.model.dto.SaveUserRequest;
import com.github.demoapp.secutiry.Base64Encoder;
import com.github.demoapp.secutiry.PasswordHasher;
import com.github.demoapp.secutiry.impl.BCryptPassword;
import com.github.demoapp.util.ApplicationContext;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Optional;

import static com.github.demoapp.util.Help.requestDispatcher;

@WebServlet("/profile")
@MultipartConfig
public class ProfileServlet extends HttpServlet {
    private static PasswordHasher passwordHasher;

    @Override
    public void init(ServletConfig config) throws ServletException {
        passwordHasher = new BCryptPassword();
    }

    private static SaveUserRequest generateSaveRequest(HttpServletRequest req, User user, String profileImage) {
        return SaveUserRequest.builder()
                .id(user.getId())
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .email(req.getParameter("email"))
                .password(user.getPassword())
                .userProfileURL(profileImage)
                .build();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");

            String encoded = changeUserProfile(req, user);
            changeUserPassword(req, user);

            SaveUserRequest build = generateSaveRequest(req, user, encoded);
            Optional<User> userOptional = ApplicationContext.getUserService().update(build);

            session.setAttribute("user", userOptional.get());

        } catch (Exception e) {
            switch (e) {
                case NotUniqueEmailException notUniqueEmailException ->
                        requestDispatcher("/index.jsp", "message", e.getMessage(), req, resp);
                case PasswordDoesntMatchException passwordDoesntMatchException ->
                        requestDispatcher("/index.jsp", "message", e.getMessage(), req, resp);
                case FileIsBigException fileIsBigException ->
                        requestDispatcher("/index.jsp", "message", e.getMessage(), req, resp);
                case PasswordIsIncorrectException passwordIsIncorrectException ->
                        requestDispatcher("/index.jsp", "message", e.getMessage(), req, resp);
                default ->
                        requestDispatcher("/index.jsp", "message", "there is some problem please try again later", req, resp);
            }
        }
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    private static void changeUserPassword(HttpServletRequest req, User user) {
        Optional<String> currentPassword = Optional.ofNullable(req.getParameter("CurrentPassword"));
        if (currentPassword.isPresent() && !currentPassword.get().isBlank()) {
            Boolean verified = passwordHasher.verifyPassword(currentPassword.get(), user.getPassword());
            if (verified) {
                Optional<String> newPassword = Optional.ofNullable(req.getParameter("NewPassword"));
                Optional<String> repeatNewPassword = Optional.ofNullable(req.getParameter("RepeatNewPassword"));
                if ((newPassword.isPresent() && repeatNewPassword.isPresent()) && (!newPassword.get().isBlank() && !repeatNewPassword.get().isBlank())) {
                    if (newPassword.get().equals(repeatNewPassword.get())) {
                        user.setPassword(passwordHasher.encode(newPassword.get()));
                    } else {
                        throw new PasswordDoesntMatchException("new password and repeated password doesn't match");
                    }
                }
            } else {
                throw new PasswordIsIncorrectException("current password is wrong! try again");
            }
        }
    }

    private static String changeUserProfile(HttpServletRequest req, User user) throws IOException, ServletException {
        Part profile = req.getPart("profile");
        if (profile.getSize() > (1024 * 10)) {
            throw new FileIsBigException("file must be 10Kb or less");
        }

        if (profile.getContentType().equals("image/jpeg")) {
            byte[] bytes = profile.getInputStream().readAllBytes();
            return Base64Encoder.encodeToString(bytes);
        } else {
            return user.getProfileImage();
        }
    }
}