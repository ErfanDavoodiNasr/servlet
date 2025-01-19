package com.github.demoapp.secutiry.impl;


import at.favre.lib.crypto.bcrypt.BCrypt;
import com.github.demoapp.secutiry.PasswordHasher;


public final class BCryptPassword implements PasswordHasher {

    @Override
    public String encode(String password) {
        BCrypt.Hasher hasher = BCrypt.withDefaults();
        return hasher.hashToString(12, password.toCharArray());
    }

    @Override
    public Boolean verifyPassword(String password, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword.toCharArray());
        return result.verified;
    }
}
