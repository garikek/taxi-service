package com.software.modsen.paymentservice.service;

import com.software.modsen.paymentservice.dto.request.RideChargeRequest;

public interface RideService {
    void handleCharge(RideChargeRequest message);
}
