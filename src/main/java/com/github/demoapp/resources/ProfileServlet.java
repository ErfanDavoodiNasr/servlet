package com.github.demoapp.resources;


import com.github.demoapp.model.User;
import com.github.demoapp.model.dto.SaveUserRequest;
import com.github.demoapp.secutiry.Base64Encoder;
import com.github.demoapp.util.ApplicationContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Optional;

@WebServlet("/profile")
@MultipartConfig
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Part profile = req.getPart("profile");
            if (profile.getSize() > (1024 * 10)) {
                throw new InvalidParameterException("Image must be at least 10KB");
            }

            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");
            byte[] bytes = profile.getInputStream().readAllBytes();
            String encoded = Base64Encoder.encodeToString(bytes);

            SaveUserRequest build = generateSaveRequest(req, user, encoded);
            Optional<User> userOptional = ApplicationContext.getUserService().update(build);

            session.setAttribute("user", userOptional.get());

        } catch (Exception e) {
            req.setAttribute("message", e.getMessage());
        }
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
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
}