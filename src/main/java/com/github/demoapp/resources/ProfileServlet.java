package com.github.demoapp.resources;


import com.github.demoapp.exception.NotUniqueEmailException;
import com.github.demoapp.model.User;
import com.github.demoapp.model.dto.SaveUserRequest;
import com.github.demoapp.secutiry.Base64Encoder;
import com.github.demoapp.util.ApplicationContext;
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
            Part profile = req.getPart("profile");
            if (profile.getSize() > (1024 * 10)) {
                requestDispatcher("/index.jsp", "message", "Image must be at least 10KB", req, resp);
            }

            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");
            byte[] bytes = profile.getInputStream().readAllBytes();
            String encoded = Base64Encoder.encodeToString(bytes);

            SaveUserRequest build = generateSaveRequest(req, user, encoded);
            Optional<User> userOptional = ApplicationContext.getUserService().update(build);

            session.setAttribute("user", userOptional.get());

        } catch (Exception e) {
            if (e instanceof NotUniqueEmailException) {
                requestDispatcher("/index.jsp", "message", "this email is already taken", req, resp);
            } else {
                requestDispatcher("/index.jsp", "message", "there is some problem please try again later", req, resp);
            }
        }
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}