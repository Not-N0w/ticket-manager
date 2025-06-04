package com.labs.collectionManager.web.mapper;

import com.labs.collectionManager.model.core.Ticket;
import com.labs.collectionManager.model.core.TicketType;
import com.labs.collectionManager.web.dto.ticket.RequestTicketDto;
import com.labs.collectionManager.web.dto.ticket.ResponseTicketDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CoordinatesMapper.class, PersonMapper.class, LocationMapper.class})
public interface TicketMapper {

    @Mapping(target = "ticketType", expression = "java(ticket.getTicketType().name())")
    ResponseTicketDto toDto(Ticket ticket);

    @Mapping(target = "ticketType", expression = "java(com.labs.collectionManager.model.core.TicketType.valueOf(ticketDto.getTicketType()))")
    Ticket toEntity(RequestTicketDto ticketDto);

    List<ResponseTicketDto> toDto(List<Ticket> tickets);
}
