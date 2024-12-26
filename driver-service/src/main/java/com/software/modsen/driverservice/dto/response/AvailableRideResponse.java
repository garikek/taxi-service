package com.software.modsen.driverservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableRideResponse {
    private Long id;
    private Long rideId;
    private Long passengerId;
    private String pickupAddress;
    private String destinationAddress;
    private Double price;
}
