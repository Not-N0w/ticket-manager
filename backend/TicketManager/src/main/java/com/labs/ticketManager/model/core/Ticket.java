package com.labs.ticketManager.model.core;

import com.labs.ticketManager.model.user.User;
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
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "person_id")
    private Person person;

    @NotNull
    @Enumerated(EnumType.STRING) // is it ok?
    @Column(name = "ticket_type")
    private TicketType ticketType;

    @Column(name = "creation_date", insertable = false, updatable = false)
    @Generated
    private LocalDateTime creationDate;

    @NotNull
    @NotEmpty
    private String name;

    @Min(0)
    private int price;

    private boolean refundable;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "coordinates_id")
    private Coordinates coordinates;
}
