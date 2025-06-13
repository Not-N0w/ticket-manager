package com.labs.ticketManager.web.controller;

import com.labs.ticketManager.service.TicketService;
import com.labs.ticketManager.web.dto.ticket.RequestTicketDto;
import com.labs.ticketManager.web.dto.ticket.ResponseTicketDto;
import com.labs.ticketManager.web.mapper.TicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    @GetMapping("/all")
    public List<ResponseTicketDto> getAll() {
        return ticketMapper.toDto(ticketService.getAll());
    }

    @PostMapping("/create")
    public void create(
            @RequestBody RequestTicketDto ticket,
            @RequestHeader("Authorization") String authHeader
    ) {
        ticketService.create(ticketMapper.toEntity(ticket), authHeader);
    }

    @PostMapping("/update/{id}")
    public void create(
            @PathVariable Long id,
            @RequestBody RequestTicketDto ticket,
            @RequestHeader("Authorization") String authHeader
    ) {
        ticketService.update(id, ticketMapper.toEntity(ticket), authHeader);
    }
}
