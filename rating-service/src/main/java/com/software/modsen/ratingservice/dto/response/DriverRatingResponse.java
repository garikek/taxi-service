package com.software.modsen.ratingservice.dto.response;

import lombok.Data;

@Data
public class DriverRatingResponse {
    private Long id;
    private Long driverId;
    private Double rating;
}
