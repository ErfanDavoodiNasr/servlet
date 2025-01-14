package com.github.demoapp.service.impl;


import com.github.demoapp.model.User;
import com.github.demoapp.model.dto.LoginUserRequest;
import com.github.demoapp.model.dto.SaveUserRequest;
import com.github.demoapp.repository.UserRepository;
import com.github.demoapp.secutiry.BCryptPassword;
import com.github.demoapp.service.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> save(SaveUserRequest userRequest) {
        User user = User.builder()
                .id(userRequest.getId())
                .firstname(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .password(BCryptPassword.encodeBcrypt(userRequest.getPassword()))
                .profileImage(userRequest.getUserProfileURL())
                .build();
        return userRepository.save(user);
    }

    @Override
    public Optional<User> update(SaveUserRequest userRequest) {
        User user = User.builder()
                .id(userRequest.getId())
                .firstname(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .profileImage(userRequest.getUserProfileURL())
                .build();
        return userRepository.update(user);
    }

    @Override
    public Optional<User> findByEmailAndPassword(LoginUserRequest userRequest) {
        Optional<User> user = userRepository.findByEmail(userRequest.getEmail());
        if (user.isPresent()){
            Boolean verifiedPassword = BCryptPassword.verifyBcryptPassword(userRequest.getPassword(), user.get().getPassword());
            if (verifiedPassword){
                return user;
            }else{
                Optional.empty();
            }
        }
        return Optional.empty();
    }

}
