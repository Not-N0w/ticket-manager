package com.labs.collectionManager.repository;

import com.labs.collectionManager.model.user.Role;
import com.labs.collectionManager.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> getByLogin(String login);
    Optional<List<User>> getAll();
    void update(User user);
    void create(User user);
    void setUserRole(Long userId, Role role);
    void delete(Long userId);

}
