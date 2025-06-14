package com.labs.ticketManager.web.controller;

import com.labs.ticketManager.exceptions.ActionNotPermittedException;
import com.labs.ticketManager.model.user.Role;
import com.labs.ticketManager.model.user.User;
import com.labs.ticketManager.service.JwtService;
import com.labs.ticketManager.service.UserService;
import com.labs.ticketManager.web.dto.user.UserDto;
import com.labs.ticketManager.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtService jwtService;

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

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPER_ADMIN')")
    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return userMapper.toDto(userService.getAllUsers());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPER_ADMIN')")
    @PatchMapping("/users/{id}/block")
    public UserDto blockUser(@PathVariable Long id) {
        return userMapper.toDto(userService.blockUser(id));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPER_ADMIN')")
    @PatchMapping("/users/{id}/unblock")
    public UserDto unblockUser(@PathVariable Long id) {
        return userMapper.toDto(userService.unblockUser(id));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPER_ADMIN')")
    @DeleteMapping("/users/{id}/remove")
    public void deleteUser(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        String username = jwtService.extractUsername(authHeader.replace("Bearer ", ""));
        User actUser = userService.getUserByUsername(username);
        userService.deleteUser(id, actUser);
    }

    @ExceptionHandler(ActionNotPermittedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleActionNotPermitted(ActionNotPermittedException e) {
        return e.getMessage();
    }

}
