package com.software.modsen.ratingservice.dto.request;

import lombok.Data;

@Data
public class PassengerRatingRequest {
    private Long passengerId;
    private Double rating;
}
