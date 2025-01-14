package com.github.demoapp.secutiry;


import at.favre.lib.crypto.bcrypt.BCrypt;


public final class BCryptPassword {

    public static String encodeBcrypt(String password) {
        BCrypt.Hasher hasher = BCrypt.withDefaults();
        return hasher.hashToString(12, password.toCharArray());
    }

    public static Boolean verifyBcryptPassword(String password, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword.toCharArray());
        return result.verified;
    }
}
