package com.labs.ticketManager.model.user;

import jakarta.persistence.*;

public enum Role {
    ROLE_USER, ROLE_ADMIN, ROLE_SUPER_ADMIN;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
