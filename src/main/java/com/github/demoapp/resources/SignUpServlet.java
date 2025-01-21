package com.github.demoapp.resources;


import com.github.demoapp.exception.NotUniqueEmailException;
import com.github.demoapp.exception.ValidationException;
import com.github.demoapp.model.dto.SaveUserRequest;
import com.github.demoapp.util.ApplicationContext;
import com.github.demoapp.util.ValidatorProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.github.demoapp.util.Help.requestDispatcher;


@WebServlet("/signup/*")
public class SignUpServlet extends HttpServlet {

    private static SaveUserRequest generateSaveRequest(Optional<String> firstName, Optional<String> lastName, Optional<String> email, Optional<String> password) {
        return SaveUserRequest.builder()
                .firstName(firstName.get())
                .lastName(lastName.get())
                .email(email.get())
                .password(password.get())
                .build();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Optional<String> firstName = Optional.ofNullable(req.getParameter("firstName"));
            Optional<String> lastName = Optional.ofNullable(req.getParameter("lastName"));
            Optional<String> email = Optional.ofNullable(req.getParameter("email"));
            Optional<String> password = Optional.ofNullable(req.getParameter("password"));
            if (firstName.isPresent() && lastName.isPresent() && email.isPresent() && password.isPresent()) {
                SaveUserRequest saveUserRequest = generateSaveRequest(firstName, lastName, email, password);

                Optional<List<String>> constraint = ValidatorProvider.validate(saveUserRequest);
                if (constraint.isPresent()) {
                    throw new ValidationException(constraint.get().toString());
                } else {
                    ApplicationContext.getUserService().save(saveUserRequest);
                    requestDispatcher("/login.jsp", "message", "user successfully added", req, resp);
                }
            } else {
                req.getRequestDispatcher("/signup.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            switch (e) {
                case NotUniqueEmailException notUniqueEmailException ->
                        requestDispatcher("/signup.jsp", "message", e.getMessage(), req, resp);
                case ValidationException validationException ->
                        requestDispatcher("/signup.jsp", "message", e.getMessage(), req, resp);
                default ->
                        requestDispatcher("/signup.jsp", "message", "there is some problem please try again later", req, resp);
            }
        }
    }
}
