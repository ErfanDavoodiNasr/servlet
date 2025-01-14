package com.github.demoapp.util;


import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidatorProvider {
    private static ValidatorFactory validatorFactory;

    public static synchronized Validator getValidator() {
        if (validatorFactory == null) {
            validatorFactory = Validation.buildDefaultValidatorFactory();
        }

        return validatorFactory.getValidator();
    }
}
