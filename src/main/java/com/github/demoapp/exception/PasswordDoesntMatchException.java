package com.github.demoapp.exception;

public class PasswordDoesntMatchException extends RuntimeException {
    public PasswordDoesntMatchException(String message) {
        super(message);
    }
}
