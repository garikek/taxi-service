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
import org.springframework.web.bind.annotation.*;

@Tag(name = "Passenger ratings", description = "Endpoints for managing passenger ratings")
@RequestMapping("/api/v1/ratings/passengers")
public interface PassengerRatingApi {
    @Operation(summary = "Get list of all passenger ratings")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of passenger ratings")
    @GetMapping
    ResponseEntity<PassengerRatingResponseList> getPassengerRatings();

    @Operation(summary = "Add new passenger rating")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Passenger rating created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    ResponseEntity<PassengerRatingResponse> addPassengerRating(@RequestBody PassengerRatingRequest passengerRatingRequest);

    @Operation(summary = "Get passenger rating by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Passenger rating found"),
            @ApiResponse(responseCode = "404", description = "Passenger rating not found")
    })
    @GetMapping("/{id}")
    PassengerRatingResponse getPassengerRatingById(@Parameter(description = "ID of the passenger rating to retrieve") @PathVariable Long id);

    @Operation(summary = "Delete passenger rating by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Passenger rating deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Passenger rating not found")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void>  deletePassengerRating(@Parameter(description = "ID of the passenger rating to delete") @PathVariable Long id);

    @Operation(summary = "Update passenger rating by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Passenger rating updated successfully"),
            @ApiResponse(responseCode = "404", description = "Passenger rating not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    ResponseEntity<PassengerRatingResponse> updatePassengerRating(
            @Parameter(description = "ID of the passenger rating to update") @PathVariable Long id,
            @RequestBody PassengerRatingRequest passengerRatingRequest
    );
}
