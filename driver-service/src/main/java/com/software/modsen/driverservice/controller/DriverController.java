package com.software.modsen.driverservice.controller;

import com.software.modsen.driverservice.dto.DriverListDto;
import com.software.modsen.driverservice.dto.DriverRequest;
import com.software.modsen.driverservice.dto.DriverResponse;
import com.software.modsen.driverservice.service.DriverService;
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
@RequestMapping("/api/v1/drivers")
@Tag(name = "Drivers", description = "Endpoints for managing drivers")
public class DriverController {
    private final DriverService driverService;

    @Operation(summary = "Get list of all drivers")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of drivers")
    @GetMapping
    public ResponseEntity<DriverListDto> getDrivers() {
        return ResponseEntity.ok(driverService.getDrivers());
    }

    @Operation(summary = "Add a new driver")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Driver created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<DriverResponse> addDriver(@RequestBody DriverRequest driverRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(driverService.addDriver(driverRequest));
    }

    @Operation(summary = "Get a driver by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Driver found"),
            @ApiResponse(responseCode = "404", description = "Driver not found")
    })
    @GetMapping("/{id}")
    public DriverResponse getDriverById(
            @Parameter(description = "ID of the driver to retrieve") @PathVariable Long id) {
        return driverService.getDriverById(id);
    }

    @Operation(summary = "Delete a driver by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Driver deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Driver not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deleteDriver(
            @Parameter(description = "ID of the driver to delete") @PathVariable Long id) {
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a driver by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Driver updated successfully"),
            @ApiResponse(responseCode = "404", description = "Driver not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DriverResponse> updateDriver(
            @Parameter(description = "ID of the driver to update") @PathVariable Long id,
            @RequestBody DriverRequest driverRequest
    ) {
        return ResponseEntity.ok(driverService.updateDriver(id, driverRequest));
    }
}
