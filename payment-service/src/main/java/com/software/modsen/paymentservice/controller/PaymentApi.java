package com.software.modsen.paymentservice.controller;

import com.software.modsen.paymentservice.dto.request.ChargeRequest;
import com.software.modsen.paymentservice.dto.request.CustomerRequest;
import com.software.modsen.paymentservice.dto.response.ChargeResponse;
import com.software.modsen.paymentservice.dto.response.CustomerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Payments", description = "Endpoints for managing payments")
public interface PaymentApi {

    @Operation(summary = "Get customer details by passenger ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer found"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    ResponseEntity<CustomerResponse> getCustomer(
            @Parameter(description = "ID of the passenger to retrieve customer details for") Long passengerId);

    @Operation(summary = "Create a new customer")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Customer created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    ResponseEntity<CustomerResponse> createCustomer(CustomerRequest customerRequest);

    @Operation(summary = "Charge a customer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer charged successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    ResponseEntity<ChargeResponse> chargeCustomer(ChargeRequest chargeRequest);
}
