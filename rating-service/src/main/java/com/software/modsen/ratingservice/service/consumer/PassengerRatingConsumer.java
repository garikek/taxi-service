package com.software.modsen.ratingservice.service.consumer;

import com.rabbitmq.client.Channel;
import com.software.modsen.ratingservice.dto.request.PassengerForRating;
import com.software.modsen.ratingservice.dto.request.PassengerRatingRequest;
import com.software.modsen.ratingservice.service.PassengerRatingService;
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
public class PassengerRatingConsumer {
    private final PassengerRatingService passengerRatingService;

    @RabbitListener(queues = "${rating-service.passenger.queue}", ackMode = "MANUAL")
    public void handlePassengerRatingMessage(PassengerForRating message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            log.info(RECEIVED_MESSAGE, message);

            switch (message.getAction()) {
                case "CREATE":
                    PassengerRatingRequest createRequest = new PassengerRatingRequest();
                    createRequest.setPassengerId(message.getPassengerId());
                    createRequest.setRating(message.getRating());
                    passengerRatingService.addPassengerRating(createRequest);
                    break;
                case "UPDATE":
                    PassengerRatingRequest updateRequest = new PassengerRatingRequest();
                    updateRequest.setPassengerId(message.getPassengerId());
                    updateRequest.setRating(message.getRating());
                    passengerRatingService.updatePassengerRatingByPassengerId(message.getPassengerId(), updateRequest);
                    break;
                case "DELETE":
                    passengerRatingService.deletePassengerRatingByPassengerId(message.getPassengerId());
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
