package com.software.modsen.ratingservice.dto.request;

import lombok.Data;

@Data
public class DriverRatingRequest {
    private Long driverId;
    private Double rating;
}
