package com.github.demoapp.util;


import com.github.demoapp.repository.UserRepository;
import com.github.demoapp.repository.impl.UserRepositoryImpl;
import com.github.demoapp.service.UserService;
import com.github.demoapp.service.impl.UserServiceImpl;
import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationContext {
    private UserRepository userRepository;
    @Getter
    private UserService userService;

    static {
        userRepository = new UserRepositoryImpl();
        userService = new UserServiceImpl(userRepository);
    }
}
