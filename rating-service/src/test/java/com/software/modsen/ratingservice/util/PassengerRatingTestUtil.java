package com.software.modsen.ratingservice.util;

import com.software.modsen.ratingservice.dto.request.PassengerRatingRequest;
import com.software.modsen.ratingservice.dto.response.PassengerRatingResponse;
import com.software.modsen.ratingservice.dto.response.PassengerRatingResponseList;
import com.software.modsen.ratingservice.model.PassengerRating;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class PassengerRatingTestUtil {
    public static final Long DEFAULT_ID = 1L;
    public static final Long UPDATED_ID = 2L;
    public static final Double DEFAULT_RATING = 2.5;
    public static final Double UPDATED_RATING = 4.9;

    public PassengerRating getDefaultPassengerRating() {
        return PassengerRating.builder()
                .id(DEFAULT_ID)
                .passengerId(DEFAULT_ID)
                .rating(DEFAULT_RATING)
                .build();
    }

    public PassengerRating getUpdatedPassengerRating() {
        return PassengerRating.builder()
                .id(UPDATED_ID)
                .passengerId(UPDATED_ID)
                .rating(UPDATED_RATING)
                .build();
    }

    public PassengerRatingRequest getDefaultPassengerRatingRequest() {
        return PassengerRatingRequest.builder()
                .passengerId(DEFAULT_ID)
                .rating(DEFAULT_RATING)
                .build();
    }

    public PassengerRatingRequest getUpdatedPassengerRatingRequest() {
        return PassengerRatingRequest.builder()
                .passengerId(UPDATED_ID)
                .rating(UPDATED_RATING)
                .build();
    }

    public PassengerRatingResponse getDefaultPassengerRatingResponse() {
        return PassengerRatingResponse.builder()
                .id(DEFAULT_ID)
                .passengerId(DEFAULT_ID)
                .rating(DEFAULT_RATING)
                .build();
    }

    public PassengerRatingResponse getUpdatedPassengerRatingResponse() {
        return PassengerRatingResponse.builder()
                .id(UPDATED_ID)
                .passengerId(UPDATED_ID)
                .rating(UPDATED_RATING)
                .build();
    }

    public PassengerRatingResponseList getDefaultPassengerRatingResponseList() {
        return new PassengerRatingResponseList(
                List.of(
                        getDefaultPassengerRatingResponse(),
                        getUpdatedPassengerRatingResponse()
                )
        );
    }
}
