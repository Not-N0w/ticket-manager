package com.labs.ticketManager.dto.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
