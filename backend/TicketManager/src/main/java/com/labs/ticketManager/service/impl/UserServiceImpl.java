package com.labs.ticketManager.service.impl;

import com.labs.ticketManager.exceptions.ActionNotPermittedException;
import com.labs.ticketManager.model.user.Role;
import com.labs.ticketManager.model.user.Status;
import com.labs.ticketManager.model.user.User;
import com.labs.ticketManager.model.user.UserAvatar;
import com.labs.ticketManager.repository.UserRepository;
import com.labs.ticketManager.service.ImageService;
import com.labs.ticketManager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ImageService imageService;
    private JwtServiceImpl jwtService;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id, User actUser) {
        User user = getUserById(id);

        boolean isPermitted = (
                (actUser.getRoles().contains(Role.ROLE_SUPER_ADMIN) && !user.getRoles().contains(Role.ROLE_SUPER_ADMIN)) ||
                        (actUser.getRoles().contains(Role.ROLE_ADMIN) && !user.getRoles().contains(Role.ROLE_ADMIN))
        );
        if (isPermitted) {
            userRepository.deleteById(id);
        }
        else {
            throw new ActionNotPermittedException("You are not permitted to delete this User");
        }

    }

    @Override
    public User addRole(Long id, Role role) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
        HashSet<Role> roles = new HashSet<>(user.getRoles());
        roles.add(role);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public User blockUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
        user.setStatus(Status.BLOCKED);
        return userRepository.save(user);
    }

    @Override
    public User unblockUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
        user.setStatus(Status.ACTIVE);
        return userRepository.save(user);
    }

    @Override
    public void uploadAvatar(Long id, UserAvatar userAvatar) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
        String fileName = imageService.upload(userAvatar);
        user.setAvatar(fileName);
        userRepository.save(user);
    }

    @Override
    public User removeRole(Long id, Role role) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
        HashSet<Role> roles = new HashSet<>(user.getRoles());
        roles.remove(role);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
