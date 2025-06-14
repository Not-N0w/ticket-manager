package com.labs.ticketManager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SortItem {
    private String direction;
    private String column;
}
