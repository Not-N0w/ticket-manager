package com.labs.collectionManager.model.core;


import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.annotations.Generated;



@Data
public class Coordinates {

    @Generated
    private Long id;
    @Min(value = -47, message = "'X' value should be more than -47.")
    private int x; // Значение поля должно быть больше -47
    @Min(value = -69, message = "'Y' value should be more than -69.")
    private float y; // Значение поля должно быть больше -69
}
