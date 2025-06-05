package com.labs.ticketManager.web.mapper;

import com.labs.ticketManager.model.core.Coordinates;
import com.labs.ticketManager.web.dto.CoordinatesDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoordinatesMapper {

    CoordinatesDto toDto(Coordinates coordinates);
    Coordinates toEntity(CoordinatesDto coordinatesDto);
}
