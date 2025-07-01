package com.example.seat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Seat {
    private int id;
    private String status;
    private String passportNumber;
} 