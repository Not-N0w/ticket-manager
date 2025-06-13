package com.labs.ticketManager.web.dto.ticket;

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
