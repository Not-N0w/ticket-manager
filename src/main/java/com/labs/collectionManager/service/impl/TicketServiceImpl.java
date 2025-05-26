package com.labs.collectionManager.service.impl;

import com.labs.collectionManager.model.core.Ticket;
import com.labs.collectionManager.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    @Override
    public void create(Ticket ticket) {

    }

    @Override
    public List<Ticket> findAll() {
        return List.of();
    }

    @Override
    public void update(Ticket ticket) {

    }

    @Override
    public void delete(Long ticketId) {

    }
}
