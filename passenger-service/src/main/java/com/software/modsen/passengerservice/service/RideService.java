package com.software.modsen.passengerservice.service;

import com.software.modsen.passengerservice.dto.request.RideRequest;

public interface RideService {
    void requestRide(RideRequest rideRequest);
    void cancelRide(Long passengerId);
}
