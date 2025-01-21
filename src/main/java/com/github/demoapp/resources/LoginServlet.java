package com.github.demoapp.resources;


import com.github.demoapp.exception.EmailOrPasswordIsWrongException;
import com.github.demoapp.exception.UserNotFoundException;
import com.github.demoapp.exception.ValidationException;
import com.github.demoapp.model.User;
import com.github.demoapp.model.dto.LoginUserRequest;
import com.github.demoapp.util.ApplicationContext;
import com.github.demoapp.util.ValidatorProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.github.demoapp.util.Help.requestDispatcher;


@WebServlet("/login/*")
public class LoginServlet extends HttpServlet {

    private static String dispatcherPath(HttpSession session) {
        Optional<String> optionalServletPath = Optional.ofNullable((String) session.getAttribute("servletPath"));
        if (optionalServletPath.isPresent()) {
            return optionalServletPath.get();
        }
        return "/index.jsp";
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Optional<String> email = Optional.ofNullable(req.getParameter("email"));
            Optional<String> password = Optional.ofNullable(req.getParameter("password"));
            if (email.isPresent() && password.isPresent()) {
                LoginUserRequest loginUserRequest = LoginUserRequest.builder().email(email.get()).password(password.get()).build();
                Optional<List<String>> constraint = ValidatorProvider.validate(loginUserRequest);
                if (constraint.isPresent()) {
                    throw new ValidationException(constraint.get().toString());
                } else {
                    Optional<User> user = ApplicationContext.getUserService().findByEmailAndPassword(loginUserRequest);
                    if (user.isPresent()) {
                        HttpSession session = req.getSession();
                        session.setAttribute("user", user.get());
                        req.getRequestDispatcher(dispatcherPath(session)).forward(req, resp);
                    } else {
                        throw new UserNotFoundException("user not found");
                    }
                }
            } else {
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            switch (e) {
                case EmailOrPasswordIsWrongException notUniqueEmailException ->
                        requestDispatcher("/login.jsp", "message", e.getMessage(), req, resp);
                case ValidationException validationException ->
                        requestDispatcher("/login.jsp", "message", e.getMessage(), req, resp);
                case UserNotFoundException userNotFoundException ->
                        requestDispatcher("/signup.jsp", "message", e.getMessage(), req, resp);
                default ->
                        requestDispatcher("/login.jsp", "message", "there is some problem please try again later", req, resp);
            }
        }
    }
}
