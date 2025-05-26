package com.labs.collectionManager.model.core;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;

import java.time.LocalDateTime;

@Data
@Builder
public class Ticket {

    @Generated
    private Long id;
    @NotNull
    private Person person;
    @NotNull
    private TicketType ticketType;
    @Generated
    private LocalDateTime creationDate;
    @NotNull
    @NotEmpty
    private String name;
    @Min(0)
    private int price;
    private boolean refundable;
    @NotNull
    private Coordinates coordinates;
}
