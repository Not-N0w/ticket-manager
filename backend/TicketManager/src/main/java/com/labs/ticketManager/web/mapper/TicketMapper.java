package com.labs.ticketManager.web.mapper;

import com.labs.ticketManager.model.core.Ticket;
import com.labs.ticketManager.web.dto.ticket.RequestTicketDto;
import com.labs.ticketManager.web.dto.ticket.ResponseTicketDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CoordinatesMapper.class, PersonMapper.class, LocationMapper.class})
public interface TicketMapper {

    @Mapping(target = "ticketType", expression = "java(ticket.getTicketType().name())")
    ResponseTicketDto toDto(Ticket ticket);

    @Mapping(target = "ticketType", expression = "java(com.labs.ticketManager.model.core.TicketType.valueOf(ticketDto.getTicketType()))")
    Ticket toEntity(RequestTicketDto ticketDto);

    List<ResponseTicketDto> toDto(List<Ticket> tickets);
}
