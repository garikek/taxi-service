package com.software.modsen.driverservice.dto.request;

import lombok.Data;

@Data
public class RatingRequest {
    private Long driverId;
    private Double rating;
}
