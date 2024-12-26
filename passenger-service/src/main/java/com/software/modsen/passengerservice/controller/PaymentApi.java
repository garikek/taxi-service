package com.software.modsen.passengerservice.controller;

import com.software.modsen.passengerservice.dto.request.PaymentRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Payments", description = "Endpoints for managing passenger payments")
public interface PaymentApi {
    @Operation(summary = "Create a new customer for payments")
    @ApiResponse(responseCode = "201", description = "Customer created successfully")
    ResponseEntity<Void> createCustomer(PaymentRequest paymentRequest);
}
