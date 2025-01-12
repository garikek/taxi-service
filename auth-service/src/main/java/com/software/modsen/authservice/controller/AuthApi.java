package com.software.modsen.authservice.controller;

import com.software.modsen.authservice.dto.request.LoginRequest;
import com.software.modsen.authservice.dto.request.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "Authentication", description = "Endpoints for managing authentication")
public interface AuthApi {

    @Operation(summary = "Register a new passenger")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Passenger created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    ResponseEntity<Void> registerPassenger(RegisterRequest registerRequest);

    @Operation(summary = "Register a new driver")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Driver created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    ResponseEntity<Void> registerDriver(RegisterRequest registerRequest);

    @Operation(summary = "Login a user and retrieve an access token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User logged in successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    ResponseEntity<AccessTokenResponse> login(LoginRequest loginRequest);
}
