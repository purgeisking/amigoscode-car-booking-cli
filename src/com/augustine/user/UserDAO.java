package com.augustine.user;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class UserDAO {
   private User[] users = new User[10];
   private int size = 0;

    // Register a customer
    public void save(User user){
        if (user == null){
            throw new IllegalArgumentException("User cannot be null");
        }

        validateSize();
        users[size] = user;
        size++;
        System.out.println("User successfully saved: " + user);
    }

    // Return all customers
    public User[] findAll(){
        return Arrays.copyOf(users, size);
    }

    // Find customer using ID
    public Optional<User> findById(UUID id){
        if (id == null){
            return Optional.empty();
        }
        for (int i = 0; i < size; i++) {
            if (users[i].getId() != null && id.equals(users[i].getId())){
                return Optional.of(users[i]);
            }
        }
        return Optional.empty();
    }

    // Delete customer using ID
    public Optional<User> deleteById(UUID id){
        if (id == null){
            return Optional.empty();
        }
        for (int i = 0; i < size; i++) {
            User user = users[i];
            if (user.getId() != null && id.equals(user.getId())){

                User deleteduser = user;

                for (int j = i; j < size -1 ; j++) {
                    users[j] = users[j + 1];
                }

                users[size - 1] = null;
                size--;

                return Optional.of(deleteduser);
            }
        }


return Optional.empty();
    }



    private void validateSize(){
        if (size == users.length){
            users = Arrays.copyOf(users, users.length * 2);
        }
    }
}
