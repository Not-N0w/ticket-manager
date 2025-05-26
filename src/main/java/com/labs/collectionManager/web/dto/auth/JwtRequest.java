package com.labs.collectionManager.web.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JwtRequest {

    @NotNull(message = "Login cannot be null.")
    private String login;
    @NotNull(message = "Password cannot be null.")
    private String password;
}
