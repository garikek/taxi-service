package com.software.modsen.ratingservice.dto.response;

import lombok.Data;

@Data
public class PassengerRatingResponse {
    private Long id;
    private Long passengerId;
    private Double rating;
}
