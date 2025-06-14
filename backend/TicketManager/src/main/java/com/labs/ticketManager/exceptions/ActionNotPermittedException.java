package com.labs.ticketManager.exceptions;

public class ActionNotPermittedException extends RuntimeException {
    public ActionNotPermittedException(String message) {
        super(message);
    }
}
