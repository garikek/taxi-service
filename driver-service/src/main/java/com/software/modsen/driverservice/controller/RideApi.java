package com.software.modsen.driverservice.controller;

import com.software.modsen.driverservice.dto.request.DriverRideRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Driver Rides", description = "Endpoints for managing driver rides")
public interface RideApi {
    @Operation(summary = "Accept a new ride for a driver")
    @ApiResponse(responseCode = "200", description = "Ride accepted successfully")
    ResponseEntity<Void> acceptRide(DriverRideRequest rideRequest);

    @Operation(summary = "Finish a ride for a driver")
    @ApiResponse(responseCode = "200", description = "Ride finished successfully")
    ResponseEntity<Void> finishRide(Long driverId);
}

