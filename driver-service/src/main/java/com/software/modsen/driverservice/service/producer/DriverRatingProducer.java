package com.software.modsen.driverservice.service.producer;

import com.software.modsen.driverservice.dto.request.DriverForRating;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverRatingProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rating-service.exchange}")
    private String exchange;

    @Value("${rating-service.routing-key}")
    private String routingKey;

    public void sendDriverRatingMessage(DriverForRating driverForRating) {
        rabbitTemplate.convertAndSend(exchange, routingKey, driverForRating);
    }
}
