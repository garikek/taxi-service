package com.software.modsen.driverservice.service;

import com.software.modsen.driverservice.dto.request.DriverRideRequest;
import com.software.modsen.driverservice.dto.request.RideDriverRequest;
import com.software.modsen.driverservice.dto.response.AvailableRideResponse;

public interface AvailableRideService {
    AvailableRideResponse addRide(RideDriverRequest rideDriverRequest);
    void deleteRide(Long id);
    void deleteRide(Long rideId, Long passengerId);
    void acceptRide(DriverRideRequest rideRequest);
    void finishRide(Long driverId);
}
