package com.software.modsen.passengerservice.controller.impl;

import com.software.modsen.passengerservice.controller.PaymentApi;
import com.software.modsen.passengerservice.dto.request.PaymentRequest;
import com.software.modsen.passengerservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/passengers/payments")
public class PaymentController implements PaymentApi {
    private final PaymentService paymentService;

    @PreAuthorize("hasAnyRole('ROLE_PASSENGER','ROLE_ADMIN')")
    @Override
    @PostMapping
    public ResponseEntity<Void> createCustomer(@RequestBody PaymentRequest paymentRequest) {
        paymentService.createCustomer(paymentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
