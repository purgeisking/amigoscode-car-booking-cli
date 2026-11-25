package com.augustine.user;


import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class UserArrayDataAccessService implements UserDAO {

    private User[] users = new User[10];
    private int size = 0;

    @Override
    public void save(User user) {

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (findById(user.getId()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        validateSize();

        users[size] = user;
        size++;
    }

    @Override
    public User[] findAll() {
        return Arrays.copyOf(users, size);
    }

    @Override
    public Optional<User> findById(UUID id) {

        if (id == null) {
            return Optional.empty();
        }

        for (int i = 0; i < size; i++) {

            if (id.equals(users[i].getId())) {
                return Optional.of(users[i]);
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> deleteById(UUID id) {

        if (id == null) {
            return Optional.empty();
        }

        for (int i = 0; i < size; i++) {

            User user = users[i];

            if (id.equals(user.getId())) {

                for (int j = i; j < size - 1; j++) {
                    users[j] = users[j + 1];
                }

                users[size - 1] = null;
                size--;

                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    private void validateSize() {

        if (size == users.length) {
            users = Arrays.copyOf(users, users.length * 2);
        }
    }
}
