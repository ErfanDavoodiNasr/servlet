package com.github.demoapp.resources;




import com.github.demoapp.model.User;
import com.github.demoapp.model.dto.SaveUserRequest;
import com.github.demoapp.util.ApplicationContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.github.demoapp.util.Help.requestDispatcher;
import static com.github.demoapp.util.ValidatorProvider.getValidator;


@WebServlet("/signup/*")
public class SignUpServlet extends HttpServlet {

    private static List<String> validate(SaveUserRequest userRequest) {
        Validator validator = getValidator();
        Set<ConstraintViolation<SaveUserRequest>> validate = validator.validate(userRequest);
        List<String> problems = new ArrayList<>();
        for (ConstraintViolation<SaveUserRequest> saveUserRequestConstraintViolation : validate) {
            problems.add(saveUserRequestConstraintViolation.getMessage());
        }
        return problems;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<String> firstName = Optional.ofNullable(req.getParameter("firstName"));
        Optional<String> lastName = Optional.ofNullable(req.getParameter("lastName"));
        Optional<String> email = Optional.ofNullable(req.getParameter("email"));
        Optional<String> password = Optional.ofNullable(req.getParameter("password"));
        if (firstName.isPresent() && lastName.isPresent() && email.isPresent() && password.isPresent()){
            SaveUserRequest saveUserRequest = SaveUserRequest.builder()
                    .firstName(firstName.get())
                    .lastName(lastName.get())
                    .email(email.get())
                    .password(password.get())
                    .build();

            List<String> problems = validate(saveUserRequest);
            if (!problems.isEmpty()){
                requestDispatcher("/signup.jsp","message",problems,req,resp);
            }else{
                Optional<User> user = ApplicationContext.getUserService().save(saveUserRequest);
                requestDispatcher("/login.jsp","message","user successfully added",req,resp);
            }
        }else{
            req.getRequestDispatcher("/signup.jsp").forward(req,resp);
        }
    }
}
