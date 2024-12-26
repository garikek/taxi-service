package com.software.modsen.rideservice.service;

import com.software.modsen.rideservice.dto.response.RideListDto;
import com.software.modsen.rideservice.dto.request.RideRequest;
import com.software.modsen.rideservice.dto.response.RideResponse;

public interface RideService {
    RideListDto getRides();
    RideResponse addRide(RideRequest rideRequest);
    RideResponse getRideById(Long id);
    void deleteRide(Long id);
    RideResponse updateRide(Long id, RideRequest rideRequest);
}
