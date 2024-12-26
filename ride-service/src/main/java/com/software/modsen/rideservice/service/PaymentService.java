package com.software.modsen.rideservice.service;

import com.software.modsen.rideservice.dto.request.FinishRideRequest;

public interface PaymentService {
    void handleCompletePayment(FinishRideRequest message);
}
