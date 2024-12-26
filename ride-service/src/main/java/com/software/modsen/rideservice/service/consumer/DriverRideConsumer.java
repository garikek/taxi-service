package com.software.modsen.rideservice.service.consumer;

import com.rabbitmq.client.Channel;
import com.software.modsen.rideservice.dto.request.DriverRideRequest;
import com.software.modsen.rideservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.software.modsen.rideservice.utility.Constant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DriverRideConsumer {
    private final DriverService driverService;

    @RabbitListener(queues = "${ride-service.driver.queue}", ackMode = "MANUAL")
    public void handleDriverRideMessage(DriverRideRequest message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            log.info(RECEIVED_MESSAGE, message);

            switch (message.getAction()) {
                case "ACCEPT":
                    driverService.handleAcceptRide(message);
                    break;
                case "FINISH":
                    driverService.handleFinishRide(message);
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
