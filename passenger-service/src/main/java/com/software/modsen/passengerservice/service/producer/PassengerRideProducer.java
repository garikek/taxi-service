package com.software.modsen.passengerservice.service.producer;

import com.software.modsen.passengerservice.dto.request.RideRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassengerRideProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${ride-service.exchange}")
    private String exchange;

    @Value("${ride-service.routing-key}")
    private String routingKey;

    public void sendPassengerRideMessage(RideRequest rideRequest) {
        rabbitTemplate.convertAndSend(exchange, routingKey, rideRequest);
    }
}
