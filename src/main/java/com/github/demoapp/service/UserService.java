package com.github.demoapp.service;


import com.github.demoapp.exception.EmailOrPasswordIsWrongException;
import com.github.demoapp.exception.NotUniqueEmailException;
import com.github.demoapp.exception.UserNotFoundException;
import com.github.demoapp.model.User;
import com.github.demoapp.model.dto.LoginUserRequest;
import com.github.demoapp.model.dto.SaveUserRequest;
import com.github.demoapp.model.dto.UpdateUserRequest;

import java.util.Optional;

public interface UserService {
    Optional<User> save(SaveUserRequest userRequest) throws NotUniqueEmailException;

    Optional<User> update(UpdateUserRequest userRequest) throws NotUniqueEmailException;

    Optional<User> findByEmailAndPassword(LoginUserRequest userRequest) throws EmailOrPasswordIsWrongException, UserNotFoundException;
}
