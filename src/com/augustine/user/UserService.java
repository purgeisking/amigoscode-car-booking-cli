package com.augustine.user;

import java.util.Optional;
import java.util.UUID;


import java.util.Optional;
import java.util.UUID;

public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void registerUser(UUID id, String name) {

        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (userDAO.findById(id).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        User user = new User(id, name.trim());

        userDAO.save(user);
    }

    public User[] findAllUsers() {
        return userDAO.findAll();
    }

    public Optional<User> findUserById(UUID id) {

        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        return userDAO.findById(id);
    }

    public Optional<User> removeUser(UUID id) {

        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        return userDAO.deleteById(id);
    }
}
