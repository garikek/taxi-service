package com.software.modsen.passengerservice.service.producer;

import com.software.modsen.passengerservice.dto.request.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassengerPaymentProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${payment-service.exchange}")
    private String exchange;

    @Value("${payment-service.routing-key}")
    private String routingKey;

    public void sendPassengerPaymentMessage(PaymentRequest paymentRequest) {
        rabbitTemplate.convertAndSend(exchange, routingKey, paymentRequest);
    }
}
