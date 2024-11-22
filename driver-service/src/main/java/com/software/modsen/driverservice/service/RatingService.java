package com.software.modsen.driverservice.service;

import com.software.modsen.driverservice.dto.request.RatingRequest;

public interface RatingService {
    void addRating(RatingRequest ratingRequest);
    void updateRating(RatingRequest ratingRequest);
    void deleteRating(Long driverId);
}