package com.software.modsen.paymentservice.service.consumer;

import com.rabbitmq.client.Channel;
import com.software.modsen.paymentservice.dto.request.RideChargeRequest;
import com.software.modsen.paymentservice.service.RideService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.software.modsen.paymentservice.utility.Constant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RidePaymentConsumer {
    private final RideService rideService;

    @RabbitListener(queues = "${payment-service.ride.queue}", ackMode = "MANUAL")
    public void handleRidePaymentMessage(RideChargeRequest message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            log.info(RECEIVED_MESSAGE, message);

            switch (message.getAction()) {
                case "CHARGE":
                    rideService.handleCharge(message);
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
