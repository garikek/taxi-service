package com.software.modsen.passengerservice.controller;

import com.software.modsen.passengerservice.dto.response.DriverResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Drivers", description = "Endpoints for managing passenger's interactions with drivers")
public interface DriverApi {
    @Operation(summary = "Get driver profile")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Driver profile received successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    ResponseEntity<DriverResponse> getDriverProfile(Long driverId);
}
