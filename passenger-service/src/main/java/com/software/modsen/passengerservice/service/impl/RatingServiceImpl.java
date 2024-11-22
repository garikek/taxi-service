package com.software.modsen.passengerservice.service.impl;

import com.software.modsen.passengerservice.dto.request.PassengerForRating;
import com.software.modsen.passengerservice.dto.request.RatingRequest;
import com.software.modsen.passengerservice.service.producer.PassengerRatingProducer;
import com.software.modsen.passengerservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final PassengerRatingProducer passengerRatingProducer;

    @Override
    public void addRating(RatingRequest ratingRequest) {
        PassengerForRating message = new PassengerForRating(
                ratingRequest.getPassengerId(),
                ratingRequest.getRating(),
                "CREATE"
        );
        passengerRatingProducer.sendPassengerRatingMessage(message);
    }

    @Override
    public void updateRating(RatingRequest ratingRequest) {
        PassengerForRating message = new PassengerForRating(
                ratingRequest.getPassengerId(),
                ratingRequest.getRating(),
                "UPDATE"
        );
        passengerRatingProducer.sendPassengerRatingMessage(message);
    }

    @Override
    public void deleteRating(Long passengerId) {
        PassengerForRating message = new PassengerForRating(
                passengerId,
                null,
                "DELETE"
        );
        passengerRatingProducer.sendPassengerRatingMessage(message);
    }
}
