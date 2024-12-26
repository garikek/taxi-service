package com.software.modsen.passengerservice.service;

import com.software.modsen.passengerservice.dto.request.PaymentRequest;

public interface PaymentService {
    void createCustomer(PaymentRequest paymentRequest);
}
