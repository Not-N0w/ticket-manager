package com.labs.collectionManager.web.dto;

import com.labs.collectionManager.model.core.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {
    private LocalDate birthday;
    private double weight;
    private String passportId;
    private LocationDto location;
}
