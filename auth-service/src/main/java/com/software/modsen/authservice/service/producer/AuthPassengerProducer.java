package com.software.modsen.authservice.service.producer;

import com.software.modsen.authservice.dto.request.AuthPassengerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthPassengerProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${passenger-service.exchange}")
    private String exchange;

    @Value("${passenger-service.routing-key}")
    private String routingKey;

    public void sendAuthPassengerMessage(AuthPassengerRequest message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
