package com.labs.collectionManager.web.controller;

import com.labs.collectionManager.model.user.User;
import com.labs.collectionManager.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    private List<User> findAllUsers() {
        return userService.getAll();
    }

    //todo
}
