package com.github.demoapp.util;


import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@UtilityClass
public class Help {
    private final Object object = new Object();
    private volatile Random random;
    private volatile Gson gson;

    public String generateRandomCode() {
        if (random == null) synchronized (object) {
            if (random == null) {
                random = new Random();
            }
        }
        return String.format("%06d", random.nextInt(1_000_000));
    }

    public synchronized Gson getGson() {
        if (gson == null) synchronized (object) {
            if (gson == null) {
                gson = new Gson();
            }
        }
        return gson;
    }

    public void requestDispatcher(String path, String attributeName, String attributeValue, ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        req.setAttribute(attributeName, attributeValue);
        req.getRequestDispatcher(path).forward(req, resp);
    }

    public void requestDispatcher(String path, String attributeName, List<String> attributeValue, ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        req.setAttribute(attributeName, attributeValue);
        req.getRequestDispatcher(path).forward(req, resp);
    }
}
