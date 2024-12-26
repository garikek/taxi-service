package com.software.modsen.rideservice.service;

import com.software.modsen.rideservice.dto.request.DriverRideRequest;

public interface DriverService {
    void handleAcceptRide(DriverRideRequest message);
    void handleFinishRide(DriverRideRequest message);
}
