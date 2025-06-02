package com.labs.collectionManager.model.core;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "tickets")
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @NotNull
    @Enumerated(EnumType.STRING) // is it ok?
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
    @OneToOne
    @JoinColumn(name = "coordinates_id")
    private Coordinates coordinates;
}
