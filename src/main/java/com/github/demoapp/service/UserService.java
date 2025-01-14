package com.github.demoapp.service;




import com.github.demoapp.model.User;
import com.github.demoapp.model.dto.LoginUserRequest;
import com.github.demoapp.model.dto.SaveUserRequest;

import java.util.Optional;

public interface UserService {
    Optional<User> save(SaveUserRequest userRequest);

    Optional<User> update(SaveUserRequest userRequest);

    Optional<User> findByEmailAndPassword(LoginUserRequest userRequest);
}
