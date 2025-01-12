package com.software.modsen.passengerservice.dto.response;

import lombok.Data;

@Data
public class PassengerRatingResponse {
    private Long id;
    private Long passengerId;
    private Double rating;
}
