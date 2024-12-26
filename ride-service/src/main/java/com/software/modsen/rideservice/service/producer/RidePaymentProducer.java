package com.software.modsen.rideservice.service.producer;

import com.software.modsen.rideservice.dto.request.RideChargeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RidePaymentProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${payment-service.exchange}")
    private String exchange;

    @Value("${payment-service.routing-key}")
    private String routingKey;

    public void sendRidePaymentMessage(RideChargeRequest rideChargeRequest) {
        rabbitTemplate.convertAndSend(exchange, routingKey, rideChargeRequest);
    }
}
