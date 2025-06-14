package com.labs.ticketManager.dto.mapper;

import com.labs.ticketManager.model.core.Coordinates;
import com.labs.ticketManager.dto.ticket.CoordinatesDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoordinatesMapper {

    CoordinatesDto toDto(Coordinates coordinates);
    Coordinates toEntity(CoordinatesDto coordinatesDto);
}
