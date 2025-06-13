package com.labs.ticketManager.service.impl;

import com.labs.ticketManager.model.core.Ticket;
import com.labs.ticketManager.model.user.User;
import com.labs.ticketManager.repository.TicketRepository;
import com.labs.ticketManager.service.JwtService;
import com.labs.ticketManager.service.TicketService;
import com.labs.ticketManager.service.UserService;
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
    public void update(Long id, Ticket ticket, String authHeader) {
        User user = userService.getUserByUsername(jwtService.extractUsername(authHeader.substring(7)));
        Ticket oldTicket = ticketRepository
                            .findById(id.toString())
                            .orElseThrow(() -> new RuntimeException("Ticket not found"));
        if (!oldTicket.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not allowed to update this ticket");
        }
        oldTicket.setName(ticket.getName());
        oldTicket.setPrice(ticket.getPrice());
        oldTicket.setRefundable(ticket.isRefundable());
        oldTicket.setTicketType(ticket.getTicketType());
        oldTicket.setCoordinates(ticket.getCoordinates());
        oldTicket.setPerson(ticket.getPerson());
        ticketRepository.save(oldTicket);
    }

    @Override
    public void delete(Long id, String authHeader) {
        User user = userService.getUserByUsername(jwtService.extractUsername(authHeader.substring(7)));
        Ticket oldTicket = ticketRepository
                .findById(id.toString())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        if (!oldTicket.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not allowed to update this ticket");
        }
        ticketRepository.delete(oldTicket);
    }

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



}
