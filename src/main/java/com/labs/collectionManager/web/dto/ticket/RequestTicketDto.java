package com.labs.collectionManager.web.dto.ticket;

import com.labs.collectionManager.model.core.Coordinates;
import com.labs.collectionManager.model.core.Person;
import com.labs.collectionManager.web.dto.CoordinatesDto;
import com.labs.collectionManager.web.dto.PersonDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestTicketDto {
    private PersonDto person;
    private String ticketType;
    private String name;
    private int price;
    private boolean refundable;
    private CoordinatesDto coordinates;
}
