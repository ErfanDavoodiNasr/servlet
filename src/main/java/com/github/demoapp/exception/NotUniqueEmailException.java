package com.github.demoapp.exception;

public class NotUniqueEmailException extends RuntimeException {
    public NotUniqueEmailException(String message) {
        super(message);
    }
}
