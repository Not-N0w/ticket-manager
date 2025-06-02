package com.labs.collectionManager.model.user;

import jakarta.persistence.*;
import lombok.Data;

public enum Role {
    ROLE_USER, ROLE_ADMIN, ROLE_SUPER_ADMIN;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
