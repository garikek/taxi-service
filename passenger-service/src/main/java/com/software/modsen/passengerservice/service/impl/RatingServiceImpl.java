package com.software.modsen.passengerservice.service.impl;

import com.software.modsen.passengerservice.client.RatingFeignClient;
import com.software.modsen.passengerservice.dto.request.PassengerForRating;
import com.software.modsen.passengerservice.dto.request.RatingRequest;
import com.software.modsen.passengerservice.dto.response.PassengerRatingResponseList;
import com.software.modsen.passengerservice.service.producer.PassengerRatingProducer;
import com.software.modsen.passengerservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.software.modsen.passengerservice.utility.Constant.SENDING_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final PassengerRatingProducer passengerRatingProducer;
    private final RatingFeignClient ratingFeignClient;

    @Override
    public void addRating(RatingRequest ratingRequest) {
        PassengerForRating message = new PassengerForRating(
                ratingRequest.getPassengerId(),
                ratingRequest.getRating(),
                "CREATE"
        );
        log.info(SENDING_MESSAGE, message.getAction());
        passengerRatingProducer.sendPassengerRatingMessage(message);
    }

    @Override
    public void updateRating(RatingRequest ratingRequest) {
        PassengerForRating message = new PassengerForRating(
                ratingRequest.getPassengerId(),
                ratingRequest.getRating(),
                "UPDATE"
        );
        log.info(SENDING_MESSAGE, message.getAction());
        passengerRatingProducer.sendPassengerRatingMessage(message);
    }

    @Override
    public void deleteRating(Long passengerId) {
        PassengerForRating message = new PassengerForRating(
                passengerId,
                null,
                "DELETE"
        );
        log.info(SENDING_MESSAGE, message.getAction());
        passengerRatingProducer.sendPassengerRatingMessage(message);
    }

    @Override
    public PassengerRatingResponseList getPassengerRatings(Long passengerId) {
        return ratingFeignClient.getPassengerRatings(passengerId);
    }
}
