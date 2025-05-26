package com.labs.collectionManager.repository;

import com.labs.collectionManager.model.core.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketRepository {
    void create(Ticket ticket);
    Optional<List<Ticket>> findAll();
    void update(Ticket ticket);
    void delete(Long ticketId);
}
