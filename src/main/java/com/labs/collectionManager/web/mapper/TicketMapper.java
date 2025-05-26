package com.labs.collectionManager.web.mapper;

import com.labs.collectionManager.model.core.Ticket;
import com.labs.collectionManager.web.dto.ticket.TicketDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    TicketDto toDto(Ticket ticket);

    Ticket toEntity(TicketDto ticketDto);

    List<TicketDto> toDto(List<Ticket> tickets);
}
