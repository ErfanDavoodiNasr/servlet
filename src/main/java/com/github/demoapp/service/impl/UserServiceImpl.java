package com.github.demoapp.service.impl;


import com.github.demoapp.exception.EmailOrPasswordIsWrongException;
import com.github.demoapp.exception.NotUniqueEmailException;
import com.github.demoapp.exception.UserNotFoundException;
import com.github.demoapp.model.User;
import com.github.demoapp.model.dto.LoginUserRequest;
import com.github.demoapp.model.dto.SaveUserRequest;
import com.github.demoapp.model.dto.UpdateUserRequest;
import com.github.demoapp.repository.UserRepository;
import com.github.demoapp.secutiry.PasswordHasher;
import com.github.demoapp.secutiry.impl.BCryptPassword;
import com.github.demoapp.service.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher = new BCryptPassword();

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> save(SaveUserRequest userRequest) throws NotUniqueEmailException {
        User user = User.builder()
                .id(userRequest.getId())
                .firstname(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .password(passwordHasher.encode(userRequest.getPassword()))
                .profileImage(userRequest.getUserProfileImage())
                .build();
        return userRepository.save(user);
    }

    @Override
    public Optional<User> update(UpdateUserRequest userRequest) throws NotUniqueEmailException {
        User user = User.builder()
                .id(userRequest.getId())
                .firstname(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .profileImage(userRequest.getUserProfileImage())
                .build();
        return userRepository.update(user);
    }

    @Override
    public Optional<User> findByEmailAndPassword(LoginUserRequest userRequest) throws EmailOrPasswordIsWrongException, UserNotFoundException {
        Optional<User> user = userRepository.findByEmail(userRequest.getEmail());
        if (user.isPresent()) {
            Boolean verifiedPassword = passwordHasher.verifyPassword(userRequest.getPassword(), user.get().getPassword());
            if (verifiedPassword) {
                return user;
            } else {
                throw new EmailOrPasswordIsWrongException("email or password is wrong");
            }
        }
        throw new UserNotFoundException("user not found");
    }

}
