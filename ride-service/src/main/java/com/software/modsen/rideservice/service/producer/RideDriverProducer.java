package com.software.modsen.rideservice.service.producer;

import com.software.modsen.rideservice.dto.request.RideDriverRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideDriverProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${driver-service.exchange}")
    private String exchange;

    @Value("${driver-service.routing-key}")
    private String routingKey;

    public void sendRideDriverMessage(RideDriverRequest rideDriverRequest) {
        rabbitTemplate.convertAndSend(exchange, routingKey, rideDriverRequest);
    }
}
