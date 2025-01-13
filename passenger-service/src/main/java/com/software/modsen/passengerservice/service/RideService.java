package com.software.modsen.passengerservice.service;

import com.software.modsen.passengerservice.dto.request.RideRequest;
import com.software.modsen.passengerservice.dto.response.RideListDto;

public interface RideService {
    void requestRide(RideRequest rideRequest);
    void cancelRide(Long passengerId);
    Double getEstimatedRidePrice(Long rideId);
    RideListDto getRideHistory(Long passengerId);
}
