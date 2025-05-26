package com.labs.collectionManager.service;

import com.labs.collectionManager.model.core.Ticket;

import java.util.List;

public interface TicketService {
    void create(Ticket ticket);
    List<Ticket> findAll();
    void update(Ticket ticket);
    void delete(Long ticketId);
}
