package com.labs.ticketManager.dto.mapper;

import com.labs.ticketManager.model.core.Location;
import com.labs.ticketManager.dto.ticket.LocationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    Location toEntity(LocationDto locationDto);
    LocationDto toDto(Location location);
}
