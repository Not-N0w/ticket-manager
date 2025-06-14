package com.labs.ticketManager.web.controller;

import com.labs.ticketManager.exceptions.ActionNotPermittedException;
import com.labs.ticketManager.exceptions.IllegalDataException;
import com.labs.ticketManager.exceptions.ImageUploadException;
import com.labs.ticketManager.service.TicketService;
import com.labs.ticketManager.web.dto.ticket.RequestTicketDto;
import com.labs.ticketManager.web.dto.ticket.ResponseTicketDto;
import com.labs.ticketManager.web.mapper.TicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
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

    @DeleteMapping("/delete/{id}")
    public void delete(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader
    ) {
        ticketService.delete(id, authHeader);
    }

    @ExceptionHandler(IllegalDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalDataException(IllegalDataException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ActionNotPermittedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleActionNotPermitted(ActionNotPermittedException e) {
        return e.getMessage();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleRuntimeException(RuntimeException e) {
        return Map.of("message", e.getMessage()).toString();
    }

}
