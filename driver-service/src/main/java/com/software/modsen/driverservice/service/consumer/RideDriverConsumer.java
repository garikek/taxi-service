package com.software.modsen.driverservice.service.consumer;

import com.rabbitmq.client.Channel;
import com.software.modsen.driverservice.dto.request.RideDriverRequest;
import com.software.modsen.driverservice.service.AvailableRideService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.software.modsen.driverservice.utility.Constant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RideDriverConsumer {
    private final AvailableRideService rideService;

    @RabbitListener(queues = "${driver-service.queue}", ackMode = "MANUAL")
    public void handleRideDriverMessage(RideDriverRequest message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            log.info(RECEIVED_MESSAGE, message);

            switch (message.getAction()) {
                case "AVAILABLE":
                    rideService.addRide(message);
                    break;
                case "CANCEL":
                    rideService.deleteRide(message.getRideId(), message.getPassengerId());
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
