package com.software.modsen.passengerservice.service;

import com.software.modsen.passengerservice.dto.request.RatingRequest;

public interface RatingService {
    void addRating(RatingRequest ratingRequest);
    void updateRating(RatingRequest ratingRequest);
    void deleteRating(Long passengerId);
}
