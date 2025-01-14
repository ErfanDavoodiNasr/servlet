package com.github.demoapp.resources;


import com.github.demoapp.exception.EmailOrPasswordIsIncorrectException;
import com.github.demoapp.exception.UserNotFoundException;
import com.github.demoapp.model.User;
import com.github.demoapp.model.dto.LoginUserRequest;
import com.github.demoapp.util.ApplicationContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.github.demoapp.util.Help.requestDispatcher;
import static com.github.demoapp.util.ValidatorProvider.getValidator;


@WebServlet("/login/*")
public class LoginServlet extends HttpServlet {

    private static String dispatcherPath(HttpSession session) {
        Optional<String> optionalServletPath = Optional.ofNullable((String) session.getAttribute("servletPath"));
        if (optionalServletPath.isPresent()) {
            return optionalServletPath.get();
        }
        return "/index.jsp";
    }

    private static List<String> validate(LoginUserRequest userRequest) {
        Validator validator = getValidator();
        Set<ConstraintViolation<LoginUserRequest>> validate = validator.validate(userRequest);
        List<String> problems = new ArrayList<>();
        for (ConstraintViolation<LoginUserRequest> loginUserRequestConstraintViolation : validate) {
            problems.add(loginUserRequestConstraintViolation.getMessage());
        }
        return problems;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Optional<String> email = Optional.ofNullable(req.getParameter("email"));
            Optional<String> password = Optional.ofNullable(req.getParameter("password"));
            if (email.isPresent() && password.isPresent()) {
                LoginUserRequest loginUserRequest = LoginUserRequest.builder().email(email.get()).password(password.get()).build();
                List<String> problems = validate(loginUserRequest);
                if (!problems.isEmpty()) {
                    requestDispatcher("/login.jsp", "message", problems, req, resp);
                } else {
                    Optional<User> user = ApplicationContext.getUserService().findByEmailAndPassword(loginUserRequest);
                    if (user.isPresent()) {
                        HttpSession session = req.getSession();
                        session.setAttribute("user", user.get());
                        req.getRequestDispatcher(dispatcherPath(session)).forward(req, resp);
                    } else {
                        requestDispatcher("/signup.jsp", "message", "user not found", req, resp);
                    }
                }
            } else {
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            if (e instanceof EmailOrPasswordIsIncorrectException) {
                requestDispatcher("/login.jsp", "message", "email or password is wrong", req, resp);
            } else if (e instanceof UserNotFoundException) {
                requestDispatcher("/signup.jsp", "message", "user not found", req, resp);
            } else {
                requestDispatcher("/login.jsp", "message", "there is some problem please try again later", req, resp);
            }
        }
    }
}
