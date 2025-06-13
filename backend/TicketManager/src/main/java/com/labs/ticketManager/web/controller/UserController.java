package com.labs.ticketManager.web.controller;

import com.labs.ticketManager.model.user.User;
import com.labs.ticketManager.service.JwtService;
import com.labs.ticketManager.service.UserService;
import com.labs.ticketManager.web.dto.UserDto;
import com.labs.ticketManager.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    @GetMapping("/me")
    public UserDto getMe(
            @RequestHeader("Authorization") String authHeader
    ) {
        String username = jwtService.extractUsername(authHeader.replace("Bearer ", ""));
        User user = userService.getUserByUsername(username);
        return userMapper.toDto(user);    }
}
