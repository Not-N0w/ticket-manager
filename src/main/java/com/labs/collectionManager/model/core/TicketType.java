package com.labs.collectionManager.model.core;

public enum TicketType {
    VIP,
    USUAL,
    BUDGETARY,
    CHEAP;

    public static TicketType getById(Integer id) {
        try {
            return values()[id - 1];
        } catch (Exception exception) {
            throw new IllegalArgumentException("Invalid index for TicketType.");
        }
    }
}
