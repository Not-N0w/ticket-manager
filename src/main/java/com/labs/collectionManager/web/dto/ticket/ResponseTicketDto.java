package com.labs.collectionManager.web.dto.ticket;

import com.labs.collectionManager.model.core.Coordinates;
import com.labs.collectionManager.model.core.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTicketDto {
    private Long id;
    private Person person;
    private String ticketType;
    private LocalDateTime creationDate;
    private String name;
    private int price;
    private boolean refundable;
    private Coordinates coordinates;
}
