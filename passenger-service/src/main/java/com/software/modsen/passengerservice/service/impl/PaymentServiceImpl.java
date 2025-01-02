package com.software.modsen.passengerservice.service.impl;

import com.software.modsen.passengerservice.dto.request.PaymentRequest;
import com.software.modsen.passengerservice.service.PaymentService;
import com.software.modsen.passengerservice.service.producer.PassengerPaymentProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.software.modsen.passengerservice.utility.Constant.SENDING_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PassengerPaymentProducer paymentProducer;

    @Override
    public void createCustomer(PaymentRequest paymentRequest) {
        paymentRequest.setAction("CREATE");
        log.info(SENDING_MESSAGE, paymentRequest.getAction());
        paymentProducer.sendPassengerPaymentMessage(paymentRequest);
    }
}
