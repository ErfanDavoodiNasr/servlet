package com.github.demoapp.secutiry;

import java.util.Base64;


public final class Base64Encoder {
    private static Base64.Encoder encoder;


    public static String encodeToString(byte[] inputBytes) {
        if (encoder == null) {
            encoder = Base64.getEncoder();
        }
        return encoder.encodeToString(inputBytes);
    }

}
