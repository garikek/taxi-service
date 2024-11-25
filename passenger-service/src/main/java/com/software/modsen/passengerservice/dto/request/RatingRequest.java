package com.software.modsen.passengerservice.dto.request;

import lombok.Data;

@Data
public class RatingRequest {
    private Long passengerId;
    private Double rating;
}
