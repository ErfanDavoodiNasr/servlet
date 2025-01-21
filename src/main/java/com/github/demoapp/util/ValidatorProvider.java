package com.github.demoapp.util;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@UtilityClass
public class ValidatorProvider {
    private static final Object object = new Object();
    private static volatile ValidatorFactory validatorFactory;

    public static Validator getValidator() {
        if (validatorFactory == null) synchronized (object) {
            if (validatorFactory == null) {
                validatorFactory = Validation.buildDefaultValidatorFactory();
            }
        }

        return validatorFactory.getValidator();
    }

    public static <T> Optional<List<String>> validate(T t) {
        Set<ConstraintViolation<T>> constraint = getValidator().validate(t);
        List<String> constraintList = null;
        if (!constraint.isEmpty()) {
            constraintList = new ArrayList<>();
            for (ConstraintViolation<T> tConstraintViolation : constraint) {
                constraintList.add(tConstraintViolation.getMessage());
            }
        }
        return Optional.ofNullable(constraintList);
    }
}
