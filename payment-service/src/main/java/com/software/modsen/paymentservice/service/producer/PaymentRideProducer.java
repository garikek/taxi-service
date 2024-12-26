package com.software.modsen.paymentservice.service.producer;

import com.software.modsen.paymentservice.dto.request.FinishRideRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentRideProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${ride-service.exchange}")
    private String exchange;

    @Value("${ride-service.routing-key}")
    private String routingKey;

    public void sendPaymentRideMessage(FinishRideRequest rideRequest) {
        rabbitTemplate.convertAndSend(exchange, routingKey, rideRequest);
    }
}
