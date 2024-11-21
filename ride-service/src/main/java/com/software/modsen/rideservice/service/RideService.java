package com.software.modsen.rideservice.service;

import com.software.modsen.rideservice.dto.RideListDto;
import com.software.modsen.rideservice.dto.RideRequest;
import com.software.modsen.rideservice.dto.RideResponse;

public interface RideService {
    RideListDto getRides();
    RideResponse addRide(RideRequest rideRequest);
    RideResponse getRideById(Long id);
    void deleteRide(Long id);
    RideResponse updateRide(Long id, RideRequest rideRequest);
}
