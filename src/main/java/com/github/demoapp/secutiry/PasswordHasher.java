package com.github.demoapp.secutiry;

public interface PasswordHasher {
    String encode(String password);

    Boolean verifyPassword(String password, String hashedPassword);
}
