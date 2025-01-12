package com.software.modsen.rideservice.controller;

import com.software.modsen.rideservice.dto.response.RideListDto;
import com.software.modsen.rideservice.dto.request.RideRequest;
import com.software.modsen.rideservice.dto.response.RideResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Rides", description = "Endpoints for managing rides")
public interface RideApi {
    @Operation(summary = "Get list of all rides")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of rides")
    ResponseEntity<RideListDto> getRides();

    @Operation(summary = "Add a new ride")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ride created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    ResponseEntity<RideResponse> addRide(RideRequest rideRequest);

    @Operation(summary = "Get a ride by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ride found"),
            @ApiResponse(responseCode = "404", description = "Ride not found")
    })
    RideResponse getRideById(@Parameter(description = "ID of the ride to retrieve") Long id);

    @Operation(summary = "Delete a ride by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ride deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Ride not found")
    })
    ResponseEntity<Void> deleteRide(@Parameter(description = "ID of the ride to delete") Long id);

    @Operation(summary = "Update a ride by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ride updated successfully"),
            @ApiResponse(responseCode = "404", description = "Ride not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    ResponseEntity<RideResponse> updateRide(
            @Parameter(description = "ID of the ride to update") Long id,
            RideRequest rideRequest
    );

    @Operation(summary = "Get list of all completed rides by driver ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of completed rides by driver ID")
    ResponseEntity<RideListDto> getCompletedRidesByDriverId(Long driverId);

    @Operation(summary = "Get list of all completed rides by passenger ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of completed rides by passenger ID")
    ResponseEntity<RideListDto> getCompletedRidesByPassengerId(Long passengerId);

    @Operation(summary = "Get estimated price of a ride by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ride found"),
            @ApiResponse(responseCode = "404", description = "Ride not found")
    })
    Double getEstimatedRidePrice(@Parameter(description = "ID of the ride to retrieve") Long id);
}
