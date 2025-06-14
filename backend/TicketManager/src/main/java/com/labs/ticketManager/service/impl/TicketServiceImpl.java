package com.labs.ticketManager.service.impl;

import com.labs.ticketManager.exceptions.ActionNotPermittedException;
import com.labs.ticketManager.exceptions.IllegalDataException;
import com.labs.ticketManager.model.SortItem;
import com.labs.ticketManager.model.core.Ticket;
import com.labs.ticketManager.model.user.Role;
import com.labs.ticketManager.model.user.User;
import com.labs.ticketManager.repository.TicketRepository;
import com.labs.ticketManager.service.JwtService;
import com.labs.ticketManager.service.TicketService;
import com.labs.ticketManager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final JwtService jwtService;

    private Comparator<Ticket> getComparatorForField(String field) {
        return switch (field.toLowerCase()) {
            case "id" -> Comparator.comparing(Ticket::getId, Comparator.nullsLast(Long::compareTo));
            case "name" -> Comparator.comparing(Ticket::getName, Comparator.nullsLast(String::compareToIgnoreCase));
            case "price" -> Comparator.comparing(Ticket::getPrice, Comparator.nullsLast(Integer::compareTo));
            case "tickettype" -> Comparator.comparing(Ticket::getTicketType, Comparator.nullsLast(Enum::compareTo));
            case "refundable" -> Comparator.comparing(Ticket::getRefundable, Comparator.nullsLast(Boolean::compareTo));
            default -> null;
        };
    }

    @Override
    public List<Ticket> getAllSorted(List<SortItem> sortItems) {
        List<Ticket> tickets = getAll();
        Comparator<Ticket> comparator = null;

        for (SortItem item : sortItems) {
            Comparator<Ticket> fieldComparator = getComparatorForField(item.getColumn());
            if (fieldComparator == null) continue;
            if ("DESC".equalsIgnoreCase(item.getDirection())) {
                fieldComparator = fieldComparator.reversed();
            }
            if (comparator == null) {
                comparator = fieldComparator;
            } else {
                comparator = comparator.thenComparing(fieldComparator);
            }
        }

        if (comparator != null) {
            tickets.sort(comparator);
        }
        return tickets;
    }

    @Override
    public Ticket update(Long id, Ticket ticket, String authHeader) {
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
        return ticketRepository.save(oldTicket);
    }

    @Override
    public Ticket getById(Long id) {
        return ticketRepository.findById(id.toString()).orElse(null);
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
