package com.software.modsen.rideservice.dto;

import lombok.Data;

@Data
public class RideResponse {
    private Long id;
    private Long passengerId;
    private Long driverId;
    private String pickupAddress;
    private String destinationAddress;
    private Double price;
    private String status;
}