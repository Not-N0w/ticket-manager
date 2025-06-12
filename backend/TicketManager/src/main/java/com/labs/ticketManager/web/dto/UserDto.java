package com.labs.ticketManager.web.dto;

import com.labs.ticketManager.model.user.Role;
import com.labs.ticketManager.model.user.Status;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private Set<Role> roles;
    private Status status;
}
