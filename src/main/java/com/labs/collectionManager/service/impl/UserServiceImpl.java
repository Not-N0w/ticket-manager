package com.labs.collectionManager.service.impl;

import com.labs.collectionManager.model.user.Role;
import com.labs.collectionManager.model.user.User;
import com.labs.collectionManager.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public User getByLogin(String login) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return List.of();
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
