package com.software.modsen.ratingservice.service.consumer;

import com.rabbitmq.client.Channel;
import com.software.modsen.ratingservice.dto.request.DriverForRating;
import com.software.modsen.ratingservice.dto.request.DriverRatingRequest;
import com.software.modsen.ratingservice.service.DriverRatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.software.modsen.ratingservice.utility.Constant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DriverRatingConsumer {
    private final DriverRatingService driverRatingService;

    @RabbitListener(queues = "${rating-service.driver.queue}", ackMode = "MANUAL")
    public void handleDriverRatingMessage(DriverForRating message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            log.info(RECEIVED_MESSAGE, message);

            switch (message.getAction()) {
                case "CREATE":
                    DriverRatingRequest createRequest = new DriverRatingRequest();
                    createRequest.setDriverId(message.getDriverId());
                    createRequest.setRating(message.getRating());
                    driverRatingService.addDriverRating(createRequest);
                    break;
                case "UPDATE":
                    DriverRatingRequest updateRequest = new DriverRatingRequest();
                    updateRequest.setDriverId(message.getDriverId());
                    updateRequest.setRating(message.getRating());
                    driverRatingService.updateDriverRatingByDriverId(message.getDriverId(), updateRequest);
                    break;
                case "DELETE":
                    driverRatingService.deleteDriverRatingByDriverId(message.getDriverId());
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
