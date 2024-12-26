package com.software.modsen.driverservice.service.producer;

import com.software.modsen.driverservice.dto.request.DriverRideRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverRideProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${ride-service.exchange}")
    private String exchange;

    @Value("${ride-service.routing-key}")
    private String routingKey;

    public void sendDriverRideMessage(DriverRideRequest rideRequest) {
        rabbitTemplate.convertAndSend(exchange, routingKey, rideRequest);
    }
}
