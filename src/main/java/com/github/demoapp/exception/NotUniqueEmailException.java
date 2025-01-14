package com.github.demoapp.exception;

public class NotUniqueEmailException extends Exception {
    public NotUniqueEmailException(String message) {
        super(message);
    }
}
