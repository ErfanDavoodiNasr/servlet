package com.github.demoapp.resources.filter;


import com.github.demoapp.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;


public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        Optional<HttpSession> session = Optional.ofNullable(httpServletRequest.getSession(false));
        if (session.isPresent()) {
            Optional<User> user = Optional.ofNullable((User) session.get().getAttribute("user"));
            if (user.isPresent()) {
                chain.doFilter(req, resp);
            } else {
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        } else {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
