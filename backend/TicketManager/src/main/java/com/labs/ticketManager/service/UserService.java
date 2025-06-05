package com.labs.ticketManager.service;

import com.labs.ticketManager.model.user.User;

public interface UserService {
    User getUserById(Long id);
    User getUserByUsername(String username);
}
