package com.labs.collectionManager.web.mapper;

import com.labs.collectionManager.model.core.Coordinates;
import com.labs.collectionManager.web.dto.CoordinatesDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CoordinatesMapper {

    CoordinatesDto toDto(Coordinates coordinates);
    Coordinates toEntity(CoordinatesDto coordinatesDto);
}
