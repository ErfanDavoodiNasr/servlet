package com.github.demoapp.exception;

public class FileIsLargeException extends RuntimeException {
    public FileIsLargeException(String message) {
        super(message);
    }
}
