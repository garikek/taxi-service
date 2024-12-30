package com.software.modsen.driverservice.service.impl;

import com.software.modsen.driverservice.dto.request.DriverForRating;
import com.software.modsen.driverservice.dto.request.RatingRequest;
import com.software.modsen.driverservice.service.RatingService;
import com.software.modsen.driverservice.service.producer.DriverRatingProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.software.modsen.driverservice.utility.Constant.SENDING_MESSAGE;

@Slf4j
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
        log.info(SENDING_MESSAGE, message.getAction());
        driverRatingProducer.sendDriverRatingMessage(message);
    }

    @Override
    public void updateRating(RatingRequest ratingRequest) {
        DriverForRating message = new DriverForRating(
                ratingRequest.getDriverId(),
                ratingRequest.getRating(),
                "UPDATE"
        );
        log.info(SENDING_MESSAGE, message.getAction());
        driverRatingProducer.sendDriverRatingMessage(message);
    }

    @Override
    public void deleteRating(Long driverId) {
        DriverForRating message = new DriverForRating(
                driverId,
                null,
                "DELETE"
        );
        log.info(SENDING_MESSAGE, message.getAction());
        driverRatingProducer.sendDriverRatingMessage(message);
    }
}
