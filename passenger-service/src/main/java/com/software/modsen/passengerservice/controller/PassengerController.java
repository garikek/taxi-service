package com.software.modsen.passengerservice.controller;

import com.software.modsen.passengerservice.dto.response.PassengerListDTO;
import com.software.modsen.passengerservice.dto.request.PassengerRequest;
import com.software.modsen.passengerservice.dto.response.PassengerResponse;
import com.software.modsen.passengerservice.service.PassengerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/passengers")
@Tag(name = "Passengers", description = "Endpoints for managing passengers")
public class PassengerController {
    private final PassengerService passengerService;

    @Operation(summary = "Get list of all passengers")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of passengers")
    @GetMapping
    public ResponseEntity<PassengerListDTO> getPassengers() {
        return ResponseEntity.ok(passengerService.getPassengers());
    }

    @Operation(summary = "Add a new passenger")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Passenger created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<PassengerResponse> addPassenger(@RequestBody PassengerRequest passengerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(passengerService.addPassenger(passengerRequest));
    }

    @Operation(summary = "Get a passenger by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Passenger found"),
            @ApiResponse(responseCode = "404", description = "Passenger not found")
    })
    @GetMapping("/{id}")
    public PassengerResponse getPassengerById(
            @Parameter(description = "ID of the passenger to retrieve") @PathVariable Long id) {
        return passengerService.getPassengerById(id);
    }

    @Operation(summary = "Delete a passenger by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Passenger deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Passenger not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deletePassenger(
            @Parameter(description = "ID of the passenger to delete") @PathVariable Long id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a passenger by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Passenger updated successfully"),
            @ApiResponse(responseCode = "404", description = "Passenger not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PassengerResponse> updatePassenger(
            @Parameter(description = "ID of the passenger to update") @PathVariable Long id,
            @RequestBody PassengerRequest passengerRequest
    ) {
        return ResponseEntity.ok(passengerService.updatePassenger(id, passengerRequest));
    }

}