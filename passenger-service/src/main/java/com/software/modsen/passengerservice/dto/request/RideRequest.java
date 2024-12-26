package com.software.modsen.passengerservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideRequest {
    private Long passengerId;
    private String pickupAddress;
    private String destinationAddress;
    private Double promoCode;
    private String action;
}
