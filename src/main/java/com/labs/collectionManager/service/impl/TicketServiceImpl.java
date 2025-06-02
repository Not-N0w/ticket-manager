package com.labs.collectionManager.service.impl;

import com.labs.collectionManager.model.core.Ticket;
import com.labs.collectionManager.repository.TicketRepository;
import com.labs.collectionManager.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {

    TicketRepository ticketRepository;

    @Override
    public void create(Ticket ticket) {

    }

    @Override
    public List<Ticket> getAll() {
        var result = ticketRepository.findAll();
        return result.orElseGet(List::of);
    }

    @Override
    public void update(Ticket ticket) {

    }

    @Override
    public void delete(Long ticketId) {

    }
}
