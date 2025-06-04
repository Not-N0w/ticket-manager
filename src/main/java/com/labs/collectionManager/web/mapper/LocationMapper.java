package com.labs.collectionManager.web.mapper;

import com.labs.collectionManager.model.core.Location;
import com.labs.collectionManager.web.dto.LocationDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    Location toEntity(LocationDto locationDto);
    LocationDto toDto(Location location);
}
