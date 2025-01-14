package com.github.demoapp.service;


import com.github.demoapp.exception.EmailOrPasswordIsIncorrectException;
import com.github.demoapp.exception.NotUniqueEmailException;
import com.github.demoapp.exception.UserNotFoundException;
import com.github.demoapp.model.User;
import com.github.demoapp.model.dto.LoginUserRequest;
import com.github.demoapp.model.dto.SaveUserRequest;

import java.util.Optional;

public interface UserService {
    Optional<User> save(SaveUserRequest userRequest) throws NotUniqueEmailException;

    Optional<User> update(SaveUserRequest userRequest) throws NotUniqueEmailException;

    Optional<User> findByEmailAndPassword(LoginUserRequest userRequest) throws EmailOrPasswordIsIncorrectException, UserNotFoundException;
}
