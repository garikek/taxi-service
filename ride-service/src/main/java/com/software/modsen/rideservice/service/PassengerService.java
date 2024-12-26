package com.software.modsen.rideservice.service;

import com.software.modsen.rideservice.dto.request.PassengerRideRequest;
import com.software.modsen.rideservice.dto.response.RideResponse;

public interface PassengerService {
    RideResponse handleRequestRide(PassengerRideRequest message);
    void handleCancelRide(PassengerRideRequest message);
}
