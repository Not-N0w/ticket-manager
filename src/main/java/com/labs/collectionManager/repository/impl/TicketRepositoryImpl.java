package com.labs.collectionManager.repository.impl;

import com.labs.collectionManager.model.core.Ticket;
import com.labs.collectionManager.repository.TicketRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TicketRepositoryImpl implements TicketRepository {
    @Override
    public void create(Ticket ticket) {

    }

    @Override
    public Optional<List<Ticket>> findAll() {
        return Optional.empty();
    }

    @Override
    public void update(Ticket ticket) {

    }

    @Override
    public void delete(Long ticketId) {

    }
}
