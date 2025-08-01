package com.labs.ticketManager.model.core;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Float x; // Поле не может быть null
    @NotNull
    private Float y; // Поле не может быть null
    @NotNull
    private Long z; // Поле не может быть null

}
