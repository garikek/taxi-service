package com.software.modsen.driverservice.controller;

import com.software.modsen.driverservice.dto.request.RatingRequest;
import com.software.modsen.driverservice.dto.response.DriverRatingResponseList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get driver's ratings")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of driver's ratings received successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    ResponseEntity<DriverRatingResponseList> getDriverRatings(Long driverId);
}
