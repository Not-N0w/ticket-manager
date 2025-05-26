package com.labs.collectionManager.model.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class User {

    private Long id;

    @NonNull
    @NotEmpty
    private String login;

    @Builder.Default
    private Role role = Role.ROLE_USER;

    @NonNull
    private String password;

    @Builder.Default
    private boolean isVerified = false;
}
