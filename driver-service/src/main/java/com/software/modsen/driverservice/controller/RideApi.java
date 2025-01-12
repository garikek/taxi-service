package com.software.modsen.driverservice.controller;

import com.software.modsen.driverservice.dto.request.DriverRideRequest;
import com.software.modsen.driverservice.dto.response.AvailableRideListDto;
import com.software.modsen.driverservice.dto.response.RideListDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Rides", description = "Endpoints for managing rides")
public interface RideApi {
    @Operation(summary = "Get list of all available rides")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of available rides")
    ResponseEntity<AvailableRideListDto> getAvailableRides();

    @Operation(summary = "Accept a new ride for a driver")
    @ApiResponse(responseCode = "200", description = "Ride accepted successfully")
    ResponseEntity<Void> acceptRide(DriverRideRequest rideRequest);

    @Operation(summary = "Finish a ride for a driver")
    @ApiResponse(responseCode = "200", description = "Ride finished successfully")
    ResponseEntity<Void> finishRide(Long driverId);

    @Operation(summary = "Get ride history")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ride history received successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    ResponseEntity<RideListDto> getRideHistory(Long driverId);
}
