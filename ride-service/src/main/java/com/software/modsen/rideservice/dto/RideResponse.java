package com.software.modsen.rideservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideResponse {
    private Long id;
    private Long passengerId;
    private Long driverId;
    private String pickupAddress;
    private String destinationAddress;
    private Double price;
    private String status;
}
