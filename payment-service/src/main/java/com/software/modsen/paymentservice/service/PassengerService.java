package com.software.modsen.paymentservice.service;

import com.software.modsen.paymentservice.dto.request.PaymentRequest;

public interface PassengerService {
    void handleCreateCustomer(PaymentRequest message);
}
