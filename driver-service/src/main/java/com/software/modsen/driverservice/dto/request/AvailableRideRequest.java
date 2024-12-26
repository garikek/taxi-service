package com.software.modsen.driverservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableRideRequest {
    private Long rideId;
    private Long passengerId;
    private String pickupAddress;
    private String destinationAddress;
    private Double price;
}
