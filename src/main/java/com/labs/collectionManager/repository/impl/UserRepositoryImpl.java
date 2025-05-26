package com.labs.collectionManager.repository.impl;

import com.labs.collectionManager.model.user.Role;
import com.labs.collectionManager.model.user.User;
import com.labs.collectionManager.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Override
    public Optional<User> getByLogin(String login) {
        return Optional.empty();
    }

    @Override
    public Optional<List<User>> getAll() {
        return Optional.empty();
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void create(User user) {

    }

    @Override
    public void setUserRole(Long userId, Role role) {

    }

    @Override
    public void delete(Long userId) {

    }
}
