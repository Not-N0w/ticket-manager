package com.labs.collectionManager.service.impl;

import com.labs.collectionManager.model.core.Ticket;
import com.labs.collectionManager.model.user.User;
import com.labs.collectionManager.repository.TicketRepository;
import com.labs.collectionManager.repository.UserRepository;
import com.labs.collectionManager.service.JwtService;
import com.labs.collectionManager.service.TicketService;
import com.labs.collectionManager.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public void create(Ticket ticket, String authHeader) {
        User user = userService.getUserByUsername(jwtService.extractUsername(authHeader.substring(7)));
        ticket.setUser(user);
        ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> getAll() {
        Iterable<Ticket> iterable = ticketRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }


    @Override
    public void update(Ticket ticket) {

    }

    @Override
    public void delete(Long ticketId) {

    }
}
