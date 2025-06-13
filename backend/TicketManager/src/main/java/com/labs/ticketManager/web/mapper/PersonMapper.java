package com.labs.ticketManager.web.mapper;

import com.labs.ticketManager.model.core.Person;
import com.labs.ticketManager.web.dto.ticket.PersonDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {LocationMapper.class})
public interface PersonMapper {

    Person toEntity(PersonDto personDto);
    PersonDto toDto(Person person);
}
