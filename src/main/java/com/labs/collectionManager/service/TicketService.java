package com.labs.collectionManager.service;

import com.labs.collectionManager.model.core.Ticket;

import java.util.List;

public interface TicketService {
    void create(Ticket ticket, String authHeader);
    List<Ticket> getAll();
    void update(Ticket ticket);
    void delete(Long ticketId);
}
