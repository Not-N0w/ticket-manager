package com.labs.collectionManager.service;

import com.labs.collectionManager.model.user.Role;
import com.labs.collectionManager.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User getByLogin(String login);
    List<User> getAll();
    void update(User user);
    void create(User user);
    void setUserRole(Long userId, Role role);
    void delete(Long userId);
}
