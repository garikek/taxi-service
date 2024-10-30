package com.software.modsen.passengerservice.controller;

import com.software.modsen.passengerservice.dto.PassengerListDTO;
import com.software.modsen.passengerservice.dto.PassengerRequest;
import com.software.modsen.passengerservice.dto.PassengerResponse;
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

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/passengers")
@Tag(name = "Passengers", description = "Endpoints for managing passengers")
public class PassengerController {
    private final PassengerService passengerService;

    @Operation(summary = "Get list of all passengers")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of passengers")
    @GetMapping
    public PassengerListDTO getPassengers() {
        return passengerService.getPassengers();
    }

    @Operation(summary = "Add a new passenger")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Passenger created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<PassengerResponse> addPassenger(@RequestBody PassengerRequest passengerRequest) {
        PassengerResponse createdPassenger = passengerService.addPassenger(passengerRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPassenger.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdPassenger);
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void  deletePassenger(
            @Parameter(description = "ID of the passenger to delete") @PathVariable Long id) {
        passengerService.deletePassenger(id);
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
        return ResponseEntity.ok().body(passengerService.updatePassenger(id, passengerRequest));
    }

}