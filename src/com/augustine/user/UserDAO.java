package com.augustine.user;

import java.util.Optional;
import java.util.UUID;

public interface UserDAO {
    public void save(User user);
    public User[] findAll();
    public Optional<User> findById(UUID id);
    public Optional<User> deleteById(UUID id);
}
