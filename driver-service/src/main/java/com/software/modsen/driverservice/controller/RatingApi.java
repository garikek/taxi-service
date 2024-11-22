package com.software.modsen.driverservice.controller;

import com.software.modsen.driverservice.dto.request.RatingRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Ratings", description = "Endpoints for managing driver ratings")
public interface RatingApi {
    @Operation(summary = "Add a new rating for a driver")
    @ApiResponse(responseCode = "201", description = "Rating created successfully")
    ResponseEntity<Void> addRating(RatingRequest ratingRequest);

    @Operation(summary = "Update a rating for a driver")
    @ApiResponse(responseCode = "200", description = "Rating updated successfully")
    ResponseEntity<Void> updateRating(RatingRequest ratingRequest);

    @Operation(summary = "Delete a rating for a driver")
    @ApiResponse(responseCode = "204", description = "Rating deleted successfully")
    ResponseEntity<Void> deleteRating(Long driverId);
}
