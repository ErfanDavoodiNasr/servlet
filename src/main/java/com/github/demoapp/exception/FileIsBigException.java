package com.github.demoapp.exception;

public class FileIsBigException extends RuntimeException {
    public FileIsBigException(String message) {
        super(message);
    }
}
