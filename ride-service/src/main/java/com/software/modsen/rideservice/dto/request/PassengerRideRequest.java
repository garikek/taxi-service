package com.software.modsen.rideservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerRideRequest {
    private Long passengerId;
    private String pickupAddress;
    private String destinationAddress;
    private Double promoCode;
    private String action;
}
