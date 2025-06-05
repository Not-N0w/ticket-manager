package com.labs.ticketManager.service;

import com.labs.ticketManager.model.core.Ticket;

import java.util.List;

public interface TicketService {
    void create(Ticket ticket, String authHeader);
    List<Ticket> getAll();
    void update(Ticket ticket);
    void delete(Long ticketId);
}
