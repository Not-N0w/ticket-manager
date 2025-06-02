package com.labs.collectionManager.service.impl;

import com.labs.collectionManager.model.user.Role;
import com.labs.collectionManager.model.user.User;
import com.labs.collectionManager.repository.UserRepository;
import com.labs.collectionManager.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Override
    public User getByLogin(String login) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
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
