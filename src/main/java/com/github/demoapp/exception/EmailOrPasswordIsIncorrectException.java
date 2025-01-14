package com.github.demoapp.exception;

public class EmailOrPasswordIsIncorrectException extends Exception {
    public EmailOrPasswordIsIncorrectException(String message) {
        super(message);
    }
}
