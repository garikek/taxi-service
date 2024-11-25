package com.software.modsen.driverservice.service.impl;

import com.software.modsen.driverservice.dto.request.DriverForRating;
import com.software.modsen.driverservice.dto.request.RatingRequest;
import com.software.modsen.driverservice.service.RatingService;
import com.software.modsen.driverservice.service.producer.DriverRatingProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final DriverRatingProducer driverRatingProducer;

    @Override
    public void addRating(RatingRequest ratingRequest) {
        DriverForRating message = new DriverForRating(
                ratingRequest.getDriverId(),
                ratingRequest.getRating(),
                "CREATE"
        );
        driverRatingProducer.sendDriverRatingMessage(message);
    }

    @Override
    public void updateRating(RatingRequest ratingRequest) {
        DriverForRating message = new DriverForRating(
                ratingRequest.getDriverId(),
                ratingRequest.getRating(),
                "UPDATE"
        );
        driverRatingProducer.sendDriverRatingMessage(message);
    }

    @Override
    public void deleteRating(Long driverId) {
        DriverForRating message = new DriverForRating(
                driverId,
                null,
                "DELETE"
        );
        driverRatingProducer.sendDriverRatingMessage(message);
    }
}
