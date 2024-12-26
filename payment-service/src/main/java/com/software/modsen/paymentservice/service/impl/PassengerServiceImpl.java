package com.software.modsen.paymentservice.service.impl;

import com.software.modsen.paymentservice.dto.request.CustomerRequest;
import com.software.modsen.paymentservice.dto.request.PaymentRequest;
import com.software.modsen.paymentservice.service.PassengerService;
import com.software.modsen.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PaymentService paymentService;

    @Override
    public void handleCreateCustomer(PaymentRequest message) {
        CustomerRequest customerRequest = CustomerRequest.builder()
                .passengerId(message.getPassengerId())
                .name(message.getName())
                .email(message.getEmail())
                .phoneNumber(message.getPhoneNumber())
                .amount(message.getAmount())
                .build();
        paymentService.createCustomer(customerRequest);
    }
}
