package com.github.demoapp.resources;

import com.github.demoapp.model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

import static com.github.demoapp.secutiry.Base64Encoder.decodeToString;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Optional<User> user = Optional.ofNullable((User) req.getSession().getAttribute("user"));

        if (user.isPresent() && user.get().getProfileImage() != null) {
            String referer = req.getHeader("Referer");
            String profileImage = user.get().getProfileImage();
            if (referer == null || !referer.contains("http://localhost:8080/")) {
                resp.getWriter().println(profileImage);
            } else {
                byte[] imageBytes = decodeToString(profileImage);
                resp.setContentType("image/*");
                resp.setContentLength(imageBytes.length);

                resp.getOutputStream().write(imageBytes);
            }
        }
    }
}
