package com.labs.ticketManager.service.impl;

import com.labs.ticketManager.exceptions.ActionNotPermittedException;
import com.labs.ticketManager.exceptions.IllegalDataException;
import com.labs.ticketManager.model.core.Ticket;
import com.labs.ticketManager.model.user.Role;
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
        boolean permitted = (
                (oldTicket.getUser().getId().equals(user.getId())) ||
                        (!oldTicket.getUser().getRoles().contains(Role.ROLE_SUPER_ADMIN) && user.getRoles().contains(Role.ROLE_SUPER_ADMIN)) ||
                        (
                                !oldTicket.getUser().getRoles().contains(Role.ROLE_ADMIN) &&
                                        !oldTicket.getUser().getRoles().contains(Role.ROLE_SUPER_ADMIN) &&
                                        user.getRoles().contains(Role.ROLE_ADMIN)
                        )
        );

        if (!permitted) {
            throw new ActionNotPermittedException("You are not allowed to update this ticket");
        }
        oldTicket.setName(ticket.getName());
        oldTicket.setPrice(ticket.getPrice());
        oldTicket.setRefundable(ticket.getRefundable());
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

        boolean permitted = (
                (oldTicket.getUser().getId().equals(user.getId())) ||
                (!oldTicket.getUser().getRoles().contains(Role.ROLE_SUPER_ADMIN) && user.getRoles().contains(Role.ROLE_SUPER_ADMIN)) ||
                (
                        !oldTicket.getUser().getRoles().contains(Role.ROLE_ADMIN) &&
                        !oldTicket.getUser().getRoles().contains(Role.ROLE_SUPER_ADMIN) &&
                        user.getRoles().contains(Role.ROLE_ADMIN)
                )
                );

        if (!permitted) {
            throw new ActionNotPermittedException("You are not allowed to update this ticket");
        }
        ticketRepository.delete(oldTicket);
    }

    @Override
    public void create(Ticket ticket, String authHeader) {
        User user = userService.getUserByUsername(jwtService.extractUsername(authHeader.substring(7)));
        ticket.setUser(user);
        try {
            ticketRepository.save(ticket);
        }
        catch (Exception e) {
            throw new IllegalDataException("Unable to save ticket with those parameters");
        }
    }

    @Override
    public List<Ticket> getAll() {
        Iterable<Ticket> iterable = ticketRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }



}
