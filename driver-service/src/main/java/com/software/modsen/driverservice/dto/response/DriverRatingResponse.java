package com.software.modsen.driverservice.dto.response;

import lombok.Data;

@Data
public class DriverRatingResponse {
    private Long id;
    private Long driverId;
    private Double rating;
}
