package com.labs.ticketManager.web.controller;

import com.labs.ticketManager.exceptions.ImageUploadException;
import com.labs.ticketManager.model.user.User;
import com.labs.ticketManager.model.user.UserAvatar;
import com.labs.ticketManager.service.JwtService;
import com.labs.ticketManager.service.UserService;
import com.labs.ticketManager.web.dto.user.UserAvatarDto;
import com.labs.ticketManager.web.dto.user.UserDto;
import com.labs.ticketManager.web.mapper.UserAvatarMapper;
import com.labs.ticketManager.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final UserAvatarMapper userAvatarMapper;

    @GetMapping("/me")
    public UserDto getMe(
            @RequestHeader("Authorization") String authHeader
    ) {
        String username = jwtService.extractUsername(authHeader.replace("Bearer ", ""));
        User user = userService.getUserByUsername(username);
        return userMapper.toDto(user);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public UserDto getMe(
            @PathVariable Long id
    ) {
        User user = userService.getUserById(id);
        return userMapper.toDto(user);
    }

    @PostMapping("/me/image")
    public UserDto setAvatar(
            @ModelAttribute UserAvatarDto userAvatarDto,
            @RequestHeader("Authorization") String authHeader

    ) {
        String username = jwtService.extractUsername(authHeader.replace("Bearer ", ""));
        User user = userService.getUserByUsername(username);
        UserAvatar userAvatar = userAvatarMapper.toEntity(userAvatarDto);
        userService.uploadAvatar(user.getId(), userAvatar);
        return userMapper.toDto(user);
    }

    @ExceptionHandler(ImageUploadException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleImageUploadException(ImageUploadException e) {
        return e.getMessage();
    }
}

