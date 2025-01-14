package com.github.demoapp.secutiry;

import java.util.Base64;

public class Base64Encoder {
    public static String encodeToString(byte[] inputBytes) {
        return Base64.getEncoder().encodeToString(inputBytes);
    }

}
