package com.software.modsen.passengerservice.service.producer;

import com.software.modsen.passengerservice.dto.request.PassengerForRating;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassengerRatingProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rating-service.exchange}")
    private String exchange;

    @Value("${rating-service.routing-key}")
    private String routingKey;

    public void sendPassengerRatingMessage(PassengerForRating passengerForRating) {
        rabbitTemplate.convertAndSend(exchange, routingKey, passengerForRating);
    }
}
