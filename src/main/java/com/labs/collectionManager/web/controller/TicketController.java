package com.labs.collectionManager.web.controller;

import com.labs.collectionManager.service.TicketService;
import com.labs.collectionManager.web.dto.ticket.RequestTicketDto;
import com.labs.collectionManager.web.dto.ticket.ResponseTicketDto;
import com.labs.collectionManager.web.mapper.TicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/ticket")
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

    @GetMapping("/hi")
    public String str() {
        return "hihi";
    }
}
