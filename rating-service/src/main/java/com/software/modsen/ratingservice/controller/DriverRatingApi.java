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
import org.springframework.web.bind.annotation.*;

@Tag(name = "Driver ratings", description = "Endpoints for managing driver ratings")
@RequestMapping("/api/v1/ratings/drivers")
public interface DriverRatingApi {
    @Operation(summary = "Get list of all driver ratings")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of driver ratings")
    @GetMapping
    ResponseEntity<DriverRatingResponseList> getDriverRatings();

    @Operation(summary = "Add new driver rating")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Driver rating created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    ResponseEntity<DriverRatingResponse> addDriverRating(@RequestBody DriverRatingRequest driverRatingRequest);

    @Operation(summary = "Get driver rating by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Driver rating found"),
            @ApiResponse(responseCode = "404", description = "Driver rating not found")
    })
    @GetMapping("/{id}")
    DriverRatingResponse getDriverRatingById(@Parameter(description = "ID of the driver rating to retrieve") @PathVariable Long id);

    @Operation(summary = "Delete driver rating by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Driver rating deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Driver rating not found")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void>  deleteDriverRating(@Parameter(description = "ID of the driver rating to delete") @PathVariable Long id);

    @Operation(summary = "Update driver rating by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Driver rating updated successfully"),
            @ApiResponse(responseCode = "404", description = "Driver rating not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    ResponseEntity<DriverRatingResponse> updateDriverRating(
            @Parameter(description = "ID of the driver rating to update") @PathVariable Long id,
            @RequestBody DriverRatingRequest driverRatingRequest
    );
}
