package com.software.modsen.authservice.service.producer;

import com.software.modsen.authservice.dto.request.AuthDriverRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthDriverProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${driver-service.exchange}")
    private String exchange;

    @Value("${driver-service.routing-key}")
    private String routingKey;

    public void sendAuthDriverMessage(AuthDriverRequest message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
