package com.software.modsen.paymentservice.controller.impl;

import com.software.modsen.paymentservice.controller.PaymentApi;
import com.software.modsen.paymentservice.dto.request.ChargeRequest;
import com.software.modsen.paymentservice.dto.request.CustomerRequest;
import com.software.modsen.paymentservice.dto.response.ChargeResponse;
import com.software.modsen.paymentservice.dto.response.CustomerResponse;
import com.software.modsen.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController implements PaymentApi {
    private final PaymentService paymentService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @GetMapping("/{passengerId}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable Long passengerId) {
        return ResponseEntity.ok(paymentService.getCustomerByPassengerId(passengerId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest customerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.createCustomer(customerRequest));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @PostMapping("/charge")
    public ResponseEntity<ChargeResponse> chargeCustomer(@RequestBody ChargeRequest chargeRequest) {
        return ResponseEntity.ok(paymentService.chargeCustomer(chargeRequest));
    }

}
