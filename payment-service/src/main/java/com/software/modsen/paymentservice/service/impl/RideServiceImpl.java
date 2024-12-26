package com.software.modsen.paymentservice.service.impl;

import com.software.modsen.paymentservice.dto.request.ChargeRequest;
import com.software.modsen.paymentservice.dto.request.FinishRideRequest;
import com.software.modsen.paymentservice.dto.request.RideChargeRequest;
import com.software.modsen.paymentservice.service.PaymentService;
import com.software.modsen.paymentservice.service.RideService;
import com.software.modsen.paymentservice.service.producer.PaymentRideProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {
    private final PaymentService paymentService;
    private final PaymentRideProducer rideProducer;

    @Override
    public void handleCharge(RideChargeRequest message) {
        ChargeRequest chargeRequest = ChargeRequest.builder()
                .passengerId(message.getPassengerId())
                .currency(message.getCurrency())
                .amount(message.getAmount())
                .build();
        paymentService.chargeCustomer(chargeRequest);

        FinishRideRequest rideRequest = FinishRideRequest.builder()
                .rideId(message.getRideId())
                .action("COMPLETE")
                .build();
        rideProducer.sendPaymentRideMessage(rideRequest);
    }
}
