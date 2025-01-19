package com.github.demoapp.secutiry.impl;


import at.favre.lib.crypto.bcrypt.BCrypt;
import com.github.demoapp.secutiry.PasswordHasher;


public class BCryptPassword implements PasswordHasher {
    private volatile BCrypt.Hasher hasher;
    private volatile BCrypt.Verifyer verifier;
    private final Object object = new Object();

    @Override
    public String encode(String password) {
        return getHasher().hashToString(12, password.toCharArray());
    }

    @Override
    public Boolean verifyPassword(String password, String hashedPassword) {
        return getVerifier().verify(password.toCharArray(), hashedPassword.toCharArray()).verified;
    }


    private BCrypt.Hasher getHasher() {
        if (hasher == null) synchronized (object) {
            if (hasher == null) {
                hasher = BCrypt.withDefaults();
            }
        }
        return hasher;
    }

    private BCrypt.Verifyer getVerifier() {
        if (verifier == null) synchronized (object) {
            if (verifier == null) {
                verifier = BCrypt.verifyer();
            }
        }
        return verifier;
    }

}
