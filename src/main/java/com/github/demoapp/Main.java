package com.github.demoapp;

import com.github.demoapp.model.User;
import com.github.demoapp.model.dto.LoginUserRequest;
import com.github.demoapp.util.ApplicationContext;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        LoginUserRequest user = LoginUserRequest.builder()
                .email("erfan.davoudi3831@gmail.com")
                .password("12345678")
                .build();

        Optional<User> erfan = ApplicationContext.getUserService().findByEmailAndPassword(user);
        System.out.println(erfan.get());
    }
}
