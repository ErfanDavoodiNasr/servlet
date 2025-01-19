package com.github.demoapp.exception;

public class PasswordIsIncorrectException extends RuntimeException {
    public PasswordIsIncorrectException(String message) {
        super(message);
    }
}
