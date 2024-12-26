package com.software.modsen.rideservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideChargeRequest {
    private Long passengerId;
    private Long rideId;
    private String currency;
    private Long amount;
    private String action;
}
