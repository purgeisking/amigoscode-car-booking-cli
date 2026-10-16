package com.augustine.user;

import java.util.Optional;
import java.util.UUID;

public class UserService {
    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();


    }

    public User[] findAllUsers(){
        return userDAO.getAllUsers();
    }


    public Optional<User> findUserById(UUID userId){
        User[] users = findAllUsers();
        for (User user : users) {
            if (user.getId().equals(userId)){
                return Optional.of(user);
            }
        }
       return Optional.empty();
    }
}
