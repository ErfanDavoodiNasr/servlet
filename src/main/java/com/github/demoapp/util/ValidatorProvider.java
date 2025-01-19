package com.github.demoapp.util;


import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.experimental.UtilityClass;


@UtilityClass
public class ValidatorProvider {
    private static volatile ValidatorFactory validatorFactory;
    final Object object = new Object();

    public static Validator getValidator() {
        if (validatorFactory == null) synchronized (object) {
            if (validatorFactory == null) {
                validatorFactory = Validation.buildDefaultValidatorFactory();
            }
        }

        return validatorFactory.getValidator();
    }
}
