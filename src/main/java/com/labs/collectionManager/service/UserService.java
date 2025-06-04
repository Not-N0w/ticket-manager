package com.labs.collectionManager.service;

import com.labs.collectionManager.model.core.Ticket;
import com.labs.collectionManager.model.user.User;

import java.util.List;

public interface UserService {
    User getUserById(Long id);
    User getUserByUsername(String username);
}
