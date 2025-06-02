package com.labs.collectionManager.web.controller;

import com.labs.collectionManager.service.TicketService;
import com.labs.collectionManager.web.dto.ticket.TicketDto;
import com.labs.collectionManager.web.mapper.TicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    @GetMapping("/all")
    public List<TicketDto> getAll() {
        return ticketMapper.toDto(ticketService.getAll());
    }

    @GetMapping("/hi")
    public String str() {
        return "hihi";
    }
}
