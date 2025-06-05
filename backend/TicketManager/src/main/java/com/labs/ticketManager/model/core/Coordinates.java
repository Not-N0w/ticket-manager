package com.labs.ticketManager.model.core;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coordinates")
public class Coordinates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = -47, message = "'X' value should be more than -47.")
    private int x; // Значение поля должно быть больше -47
    @Min(value = -69, message = "'Y' value should be more than -69.")
    private float y; // Значение поля должно быть больше -69
}
