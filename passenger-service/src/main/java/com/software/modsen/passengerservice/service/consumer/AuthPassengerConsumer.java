package com.software.modsen.passengerservice.service.consumer;

import com.rabbitmq.client.Channel;
import com.software.modsen.passengerservice.dto.request.AuthPassengerRequest;
import com.software.modsen.passengerservice.dto.request.PassengerRequest;
import com.software.modsen.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.software.modsen.passengerservice.utility.Constant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthPassengerConsumer {
    private final PassengerService passengerService;

    @RabbitListener(queues = "${passenger-service.auth.queue}", ackMode = "MANUAL")
    public void handleAuthPassengerMessage(AuthPassengerRequest message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            log.info(RECEIVED_MESSAGE, message);

            switch (message.getAction()) {
                case "CREATE":
                    PassengerRequest passengerRequest = PassengerRequest.builder()
                            .name(message.getName())
                            .email(message.getEmail())
                            .phoneNumber(message.getPhoneNumber())
                            .build();
                    passengerService.addPassenger(passengerRequest);
                    break;
                default:
                    log.warn(UNKNOWN_ACTION_MESSAGE, message.getAction());
            }
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.error(ERROR_PROCESSING_MESSAGE, e.getMessage());
            channel.basicReject(tag, false);
        }
    }
}
