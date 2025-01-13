package com.software.modsen.ratingservice.controller;

import com.software.modsen.ratingservice.dto.request.PassengerRatingRequest;
import com.software.modsen.ratingservice.dto.response.PassengerRatingResponse;
import com.software.modsen.ratingservice.dto.response.PassengerRatingResponseList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Passenger ratings", description = "Endpoints for managing passenger ratings")
public interface PassengerRatingApi {
    @Operation(summary = "Get list of all passenger ratings")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of passenger ratings")
    ResponseEntity<PassengerRatingResponseList> getPassengerRatings();

    @Operation(summary = "Add new passenger rating")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Passenger rating created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    ResponseEntity<PassengerRatingResponse> addPassengerRating(PassengerRatingRequest passengerRatingRequest);

    @Operation(summary = "Get passenger rating by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Passenger rating found"),
            @ApiResponse(responseCode = "404", description = "Passenger rating not found")
    })
    PassengerRatingResponse getPassengerRatingById(@Parameter(description = "ID of the passenger rating to retrieve") Long id);

    @Operation(summary = "Delete passenger rating by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Passenger rating deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Passenger rating not found")
    })
    ResponseEntity<Void>  deletePassengerRating(@Parameter(description = "ID of the passenger rating to delete") Long id);

    @Operation(summary = "Update passenger rating by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Passenger rating updated successfully"),
            @ApiResponse(responseCode = "404", description = "Passenger rating not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    ResponseEntity<PassengerRatingResponse> updatePassengerRating(
            @Parameter(description = "ID of the passenger rating to update") Long id,
            PassengerRatingRequest passengerRatingRequest
    );

    @Operation(summary = "Get list of all passenger ratings by passenger ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of passenger ratings by passenger ID")
    ResponseEntity<PassengerRatingResponseList> getPassengerRatingsByPassengerId(Long passengerId);
}
