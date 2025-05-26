package com.labs.collectionManager.model.core;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Location {

    @NotNull
    private Float x; // Поле не может быть null
    @NotNull
    private Float y; // Поле не может быть null
    @NotNull
    private Long z; // Поле не может быть null

}
