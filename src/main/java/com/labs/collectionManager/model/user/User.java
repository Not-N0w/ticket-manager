package com.labs.collectionManager.model.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class User {

    private Long id;

    @NotNull
    @NotEmpty
    private String login;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Builder.Default
    private Role role = Role.ROLE_USER;

    @NotNull
    private String password;

    @NotNull
    @NotEmpty
    private String email;

    private boolean isVerified;
}
