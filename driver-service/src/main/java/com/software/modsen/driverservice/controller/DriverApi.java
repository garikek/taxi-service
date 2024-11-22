package com.software.modsen.driverservice.controller;

import com.software.modsen.driverservice.dto.response.DriverListDto;
import com.software.modsen.driverservice.dto.request.DriverRequest;
import com.software.modsen.driverservice.dto.response.DriverResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Drivers", description = "Endpoints for managing drivers")
@RequestMapping("/api/v1/drivers")
public interface DriverApi {

    @Operation(summary = "Get list of all drivers")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of drivers")
    @GetMapping
    ResponseEntity<DriverListDto> getDrivers();

    @Operation(summary = "Add a new driver")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Driver created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    ResponseEntity<DriverResponse> addDriver(@RequestBody DriverRequest driverRequest);

    @Operation(summary = "Get a driver by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Driver found"),
            @ApiResponse(responseCode = "404", description = "Driver not found")
    })
    @GetMapping("/{id}")
    DriverResponse getDriverById(@Parameter(description = "ID of the driver to retrieve") @PathVariable Long id);

    @Operation(summary = "Delete a driver by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Driver deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Driver not found")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void>  deleteDriver(@Parameter(description = "ID of the driver to delete") @PathVariable Long id);

    @Operation(summary = "Update a driver by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Driver updated successfully"),
            @ApiResponse(responseCode = "404", description = "Driver not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    ResponseEntity<DriverResponse> updateDriver(
            @Parameter(description = "ID of the driver to update") @PathVariable Long id,
            @RequestBody DriverRequest driverRequest
    );
}
