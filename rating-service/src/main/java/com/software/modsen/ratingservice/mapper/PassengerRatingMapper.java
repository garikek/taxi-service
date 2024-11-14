package com.software.modsen.ratingservice.mapper;

import com.software.modsen.ratingservice.dto.request.PassengerRatingRequest;
import com.software.modsen.ratingservice.dto.response.PassengerRatingResponse;
import com.software.modsen.ratingservice.model.PassengerRating;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PassengerRatingMapper {
    PassengerRatingResponse toPassengerRatingDto(PassengerRating passengerRating);
    PassengerRating toPassengerRatingEntity(PassengerRatingRequest passengerRatingRequest);
    void updatePassengerRatingFromDto(PassengerRatingRequest passengerRatingRequest, @MappingTarget PassengerRating passengerRating);
}
