package com.software.modsen.passengerservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerForRating {
    private Long passengerId;
    private Double rating;
    private String action;
}