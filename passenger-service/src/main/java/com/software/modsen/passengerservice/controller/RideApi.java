package com.software.modsen.passengerservice.controller;

import com.software.modsen.passengerservice.dto.request.RideRequest;
import com.software.modsen.passengerservice.dto.response.RideListDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Rides", description = "Endpoints for managing passenger rides")
public interface RideApi {
    @Operation(summary = "Request a new ride for a passenger")
    @ApiResponse(responseCode = "201", description = "Ride requested successfully")
    ResponseEntity<Void> requestRide(RideRequest rideRequest);

    @Operation(summary = "Cancel a ride for a passenger")
    @ApiResponse(responseCode = "200", description = "Ride canceled successfully")
    ResponseEntity<Void> cancelRide(Long passengerId);

    @Operation(summary = "Get estimated price of a ride")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estimated price of a ride received successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    ResponseEntity<Double> getEstimatedRidePrice(Long rideId);

    @Operation(summary = "Get ride history")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ride history received successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    ResponseEntity<RideListDto> getRideHistory(Long passengerId);
}
