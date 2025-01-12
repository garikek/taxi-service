package com.software.modsen.ratingservice.controller;

import com.software.modsen.ratingservice.dto.request.DriverRatingRequest;
import com.software.modsen.ratingservice.dto.response.DriverRatingResponse;
import com.software.modsen.ratingservice.dto.response.DriverRatingResponseList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Driver ratings", description = "Endpoints for managing driver ratings")
public interface DriverRatingApi {
    @Operation(summary = "Get list of all driver ratings")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of driver ratings")
    ResponseEntity<DriverRatingResponseList> getDriverRatings();

    @Operation(summary = "Add new driver rating")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Driver rating created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    ResponseEntity<DriverRatingResponse> addDriverRating(DriverRatingRequest driverRatingRequest);

    @Operation(summary = "Get driver rating by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Driver rating found"),
            @ApiResponse(responseCode = "404", description = "Driver rating not found")
    })
    DriverRatingResponse getDriverRatingById(@Parameter(description = "ID of the driver rating to retrieve") Long id);

    @Operation(summary = "Delete driver rating by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Driver rating deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Driver rating not found")
    })
    ResponseEntity<Void>  deleteDriverRating(@Parameter(description = "ID of the driver rating to delete") Long id);

    @Operation(summary = "Update driver rating by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Driver rating updated successfully"),
            @ApiResponse(responseCode = "404", description = "Driver rating not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    ResponseEntity<DriverRatingResponse> updateDriverRating(
            @Parameter(description = "ID of the driver rating to update") Long id,
            DriverRatingRequest driverRatingRequest
    );

    @Operation(summary = "Get list of all driver ratings by driver ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of driver ratings by driver ID")
    ResponseEntity<DriverRatingResponseList> getDriverRatingsByDriverId(Long driverId);
}
