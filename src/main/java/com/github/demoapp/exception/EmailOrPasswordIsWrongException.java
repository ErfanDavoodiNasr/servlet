package com.github.demoapp.exception;

public class EmailOrPasswordIsWrongException extends RuntimeException {
    public EmailOrPasswordIsWrongException(String message) {
        super(message);
    }
}
