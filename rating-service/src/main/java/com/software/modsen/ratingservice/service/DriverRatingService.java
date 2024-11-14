package com.software.modsen.ratingservice.service;

import com.software.modsen.ratingservice.dto.request.DriverRatingRequest;
import com.software.modsen.ratingservice.dto.response.DriverRatingResponse;
import com.software.modsen.ratingservice.dto.response.DriverRatingResponseList;

public interface DriverRatingService {
    DriverRatingResponseList getDriverRatings();
    DriverRatingResponse addDriverRating(DriverRatingRequest driverRatingRequest);
    DriverRatingResponse getDriverRatingById(Long id);
    void deleteDriverRating(Long id);
    DriverRatingResponse updateDriverRating(Long id, DriverRatingRequest driverRatingRequest);
}
