package com.labs.collectionManager.web.mapper;

import com.labs.collectionManager.model.core.Person;
import com.labs.collectionManager.web.dto.PersonDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {LocationMapper.class})
public interface PersonMapper {

    Person toEntity(PersonDto personDto);
    PersonDto toDto(Person person);
}
