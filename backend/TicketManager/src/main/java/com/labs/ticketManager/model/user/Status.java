package com.labs.ticketManager.model.user;

import jakarta.persistence.*;


public enum Status {
    ACTIVE, BLOCKED;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
