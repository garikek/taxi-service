package com.software.modsen.ratingservice.service;

import com.software.modsen.ratingservice.dto.request.PassengerRatingRequest;
import com.software.modsen.ratingservice.dto.response.PassengerRatingResponse;
import com.software.modsen.ratingservice.dto.response.PassengerRatingResponseList;

public interface PassengerRatingService {
    PassengerRatingResponseList getPassengerRatings();
    PassengerRatingResponse addPassengerRating(PassengerRatingRequest passengerRatingRequest);
    PassengerRatingResponse getPassengerRatingById(Long id);
    void deletePassengerRating(Long id);
    PassengerRatingResponse updatePassengerRating(Long id, PassengerRatingRequest passengerRatingRequest);
    void deletePassengerRatingByPassengerId(Long passengerId);
    PassengerRatingResponse updatePassengerRatingByPassengerId(Long passengerId, PassengerRatingRequest passengerRatingRequest);
}
