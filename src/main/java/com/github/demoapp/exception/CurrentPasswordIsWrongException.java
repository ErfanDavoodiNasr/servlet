package com.github.demoapp.exception;

public class CurrentPasswordIsWrongException extends RuntimeException {
    public CurrentPasswordIsWrongException(String message) {
        super(message);
    }
}
