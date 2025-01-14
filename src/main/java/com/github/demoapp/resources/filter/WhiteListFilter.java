package com.github.demoapp.resources.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class WhiteListFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        Set<String> whiteList = new HashSet<>();
        try (
                InputStream whiteListResources = getClass().getResourceAsStream("/whitelist.txt");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(whiteListResources))
        ) {
            String item;
            while ((item = bufferedReader.readLine()) != null) {
                whiteList.add(item);
            }

        } catch (Exception e) {
            e.fillInStackTrace();
        }

        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        String servletPath = httpServletRequest.getServletPath();

        if (whiteList.contains(servletPath)) {
            req.getRequestDispatcher(servletPath).forward(req,resp);
        } else {
            setServletPathInSession(httpServletRequest);
            chain.doFilter(req, resp);
        }

    }

    private static void setServletPathInSession(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        if (Optional.ofNullable(session.getAttribute("servletPath")).isEmpty()) {
            session.setAttribute("servletPath", httpServletRequest.getServletPath());
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
