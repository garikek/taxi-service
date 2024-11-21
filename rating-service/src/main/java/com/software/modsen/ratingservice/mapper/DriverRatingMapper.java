package com.software.modsen.ratingservice.mapper;

import com.software.modsen.ratingservice.dto.request.DriverRatingRequest;
import com.software.modsen.ratingservice.dto.response.DriverRatingResponse;
import com.software.modsen.ratingservice.model.DriverRating;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DriverRatingMapper {
    DriverRatingResponse toDriverRatingDto(DriverRating driverRating);
    DriverRating toDriverRatingEntity(DriverRatingRequest driverRatingRequest);
    void updateDriverRatingFromDto(DriverRatingRequest driverRatingRequest, @MappingTarget DriverRating driverRating);
}
