package com.github.demoapp.repository;


import com.github.demoapp.exception.NotUniqueEmailException;
import com.github.demoapp.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> save(User user) throws NotUniqueEmailException;

    Optional<User> update(User user) throws NotUniqueEmailException;

    Optional<User> findById(Integer id);

    Optional<User> findByEmail(String email);
}
