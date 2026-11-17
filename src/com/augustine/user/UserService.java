package com.augustine.user;

import java.util.Optional;
import java.util.UUID;

public class UserService {
    UserDAO userDAO = new UserDAO();

    // Register a new customer
    public void registerUser(UUID id, String name){
        if (id == null){
            throw new IllegalArgumentException("Id cannot be null");
        }

        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("The name field cannot be left empty");
        }

        User user = new User(id, name);
        userDAO.save(user);
    }

    // Get all registered customers
    public User[] findAllUsers(){
        return  userDAO.findAll();
    }

    // Find customer by ID
    public Optional<User> findCustomerById(UUID id){
        if (id == null){
            throw new IllegalArgumentException("Id cannot be null");
        }

        return userDAO.findById(id);
    }

    // Remove a customer
    public Optional<User> removeCustomer(UUID id){
        if (id == null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        return userDAO.deleteById(id);
    }
}
