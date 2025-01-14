package com.github.demoapp.resources;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

import static com.github.demoapp.util.Help.requestDispatcher;

@WebServlet("/logout")
public class LogOutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<HttpSession> session = Optional.ofNullable(req.getSession());
        if (session.isPresent()) {
            session.get().setAttribute("user", null);
        }
        requestDispatcher("/login.jsp", "message", "user logout successfully", req, resp);
    }
}
