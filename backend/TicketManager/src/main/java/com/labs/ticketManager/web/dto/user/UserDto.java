package com.labs.ticketManager.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labs.ticketManager.model.user.Role;
import com.labs.ticketManager.model.user.Status;
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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String avatar;
}
