package com.labs.collectionManager.model.core;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Generated;

@Data
@Builder
public class Person {

    @Generated
    private Long id;
    @NotNull
    private java.time.LocalDate birthday; // Поле не может быть null
    @Min(value = 0, message = "'Weight' value should be more than 0.")
    private double weight; // Значение поля должно быть больше 0
    @NotNull
    private String passportID; // Строка не может быть пустой, Поле может быть null
    @NotNull
    private Location location; // Поле может быть null

}
