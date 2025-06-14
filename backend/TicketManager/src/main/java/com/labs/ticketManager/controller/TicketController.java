package com.labs.ticketManager.controller;

import com.labs.ticketManager.exceptions.ActionNotPermittedException;
import com.labs.ticketManager.exceptions.IllegalDataException;
import com.labs.ticketManager.model.SortItem;
import com.labs.ticketManager.service.TicketService;
import com.labs.ticketManager.dto.ticket.RequestTicketDto;
import com.labs.ticketManager.dto.ticket.ResponseTicketDto;
import com.labs.ticketManager.dto.mapper.TicketMapper;
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

    @PostMapping("/all-sorted")
    public List<ResponseTicketDto> getAllSorted(
            @RequestBody List<SortItem> sortItems
    ) {
        return ticketMapper.toDto(ticketService.getAllSorted(sortItems));
    }

    @PostMapping("/create")
    public void create(
            @RequestBody RequestTicketDto ticket,
            @RequestHeader("Authorization") String authHeader
    ) {
        ticketService.create(ticketMapper.toEntity(ticket), authHeader);
    }

    @PostMapping("/update/{id}")
    public ResponseTicketDto update(
            @PathVariable Long id,
            @RequestBody RequestTicketDto ticket,
            @RequestHeader("Authorization") String authHeader
    ) {
        return ticketMapper.toDto(ticketService.update(id, ticketMapper.toEntity(ticket), authHeader));
    }

    @GetMapping("/{id}")
    public ResponseTicketDto get(
            @PathVariable Long id
    ) {
        return ticketMapper.toDto(ticketService.getById(id));
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
        return Map.of("message", e.getMessage()).toString();
    }

    @ExceptionHandler(ActionNotPermittedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleActionNotPermitted(ActionNotPermittedException e) {
        return Map.of("message", e.getMessage()).toString();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleRuntimeException(RuntimeException e) {
        return Map.of("message", e.getMessage()).toString();
    }

}
