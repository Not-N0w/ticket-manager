package com.labs.ticketManager.web.controller;

import com.labs.ticketManager.model.user.Role;
import com.labs.ticketManager.model.user.User;
import com.labs.ticketManager.service.UserService;
import com.labs.ticketManager.web.dto.UserDto;
import com.labs.ticketManager.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    @PostMapping("/users/{id}/assign-admin-role")
    public UserDto assignAdminRole(@PathVariable Long id) {
        return userMapper.toDto(userService.addRole(id, Role.ROLE_ADMIN));
    }

    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    @PostMapping("/users/{id}/remove-admin-role")
    public UserDto removeAdminRole(@PathVariable Long id) {
        return userMapper.toDto(userService.removeRole(id, Role.ROLE_ADMIN));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return userMapper.toDto(userService.getAllUsers());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping("/users/{id}/block")
    public UserDto blockUser(@PathVariable Long id) {
        return userMapper.toDto(userService.blockUser(id));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping("/users/{id}/unblock")
    public UserDto unblockUser(@PathVariable Long id) {
        return userMapper.toDto(userService.unblockUser(id));
    }
}
