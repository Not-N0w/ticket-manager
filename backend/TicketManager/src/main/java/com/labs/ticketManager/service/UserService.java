package com.labs.ticketManager.service;

import com.labs.ticketManager.model.user.Role;
import com.labs.ticketManager.model.user.User;
import com.labs.ticketManager.model.user.UserAvatar;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    User getUserById(Long id);
    User getUserByUsername(String username);
    User addRole(Long id, Role role);
    User removeRole(Long id, Role role);
    List<User> getAllUsers();
    User blockUser(Long id);
    User unblockUser(Long id);
    void uploadAvatar(Long id, UserAvatar userAvatar);
}
