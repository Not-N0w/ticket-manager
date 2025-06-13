package com.labs.ticketManager.model.user;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_ADMIN, ROLE_SUPER_ADMIN;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
