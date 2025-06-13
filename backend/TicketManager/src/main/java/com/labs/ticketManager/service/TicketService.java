package com.labs.ticketManager.service;

import com.labs.ticketManager.model.core.Ticket;

import java.util.List;

public interface TicketService {
    void create(Ticket ticket, String authHeader);
    List<Ticket> getAll();
    void update(Long id, Ticket ticket, String authHeader);
    void delete(Long ticketId, String authHeader);
}
