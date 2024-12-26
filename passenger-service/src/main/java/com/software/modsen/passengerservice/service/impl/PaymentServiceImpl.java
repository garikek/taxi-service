package com.software.modsen.passengerservice.service.impl;

import com.software.modsen.passengerservice.dto.request.PaymentRequest;
import com.software.modsen.passengerservice.service.PaymentService;
import com.software.modsen.passengerservice.service.producer.PassengerPaymentProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PassengerPaymentProducer paymentProducer;

    @Override
    public void createCustomer(PaymentRequest paymentRequest) {
        paymentRequest.setAction("CREATE");
        paymentProducer.sendPassengerPaymentMessage(paymentRequest);
    }
}
