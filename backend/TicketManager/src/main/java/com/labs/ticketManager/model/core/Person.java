package com.labs.ticketManager.model.core;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@Entity
@Table(name = "persons")
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate birthday; // Поле не может быть null
    @Min(value = 0, message = "'Weight' value should be more than 0.")
    private double weight; // Значение поля должно быть больше 0

    @Column(name = "passport_id")
    @NotNull
    private String passportId; // Строка не может быть пустой, Поле может быть null

    @NotNull
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "location_id")
    private Location location; // Поле может быть null

}
