package com.labs.collectionManager.web.dto.user;

import com.labs.collectionManager.model.user.Role;
import com.labs.collectionManager.web.dto.validation.OnCreate;
import com.labs.collectionManager.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserDto {

    @NotNull(message = "Id cannot be null.", groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "Login cannot be null.", groups = {OnCreate.class, OnUpdate.class})
    @NotEmpty(message = "Login cannot be empty.", groups = {OnCreate.class, OnUpdate.class})
    private String login;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull(message = "Role cannot be empty.", groups = {OnCreate.class, OnUpdate.class})
    private Role role;


    @NotNull(message = "Email cannot be null.", groups = {OnCreate.class, OnUpdate.class})
    @NotEmpty(message = "Email cannot be empty.", groups = {OnCreate.class, OnUpdate.class})
    private String email;
}
