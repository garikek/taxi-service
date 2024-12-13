package com.software.modsen.ratingservice.util;

import com.software.modsen.ratingservice.dto.request.DriverRatingRequest;
import com.software.modsen.ratingservice.dto.response.DriverRatingResponse;
import com.software.modsen.ratingservice.dto.response.DriverRatingResponseList;
import com.software.modsen.ratingservice.model.DriverRating;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class DriverRatingTestUtil {
    public static final Long DEFAULT_ID = 1L;
    public static final Long UPDATED_ID = 2L;
    public static final Double DEFAULT_RATING = 2.5;
    public static final Double UPDATED_RATING = 4.9;

    public DriverRating getDefaultDriverRating() {
        return DriverRating.builder()
                .id(DEFAULT_ID)
                .driverId(DEFAULT_ID)
                .rating(DEFAULT_RATING)
                .build();
    }

    public DriverRating getUpdatedDriverRating() {
        return DriverRating.builder()
                .id(UPDATED_ID)
                .driverId(UPDATED_ID)
                .rating(UPDATED_RATING)
                .build();
    }

    public DriverRatingRequest getDefaultDriverRatingRequest() {
        return DriverRatingRequest.builder()
                .driverId(DEFAULT_ID)
                .rating(DEFAULT_RATING)
                .build();
    }

    public DriverRatingRequest getUpdatedDriverRatingRequest() {
        return DriverRatingRequest.builder()
                .driverId(UPDATED_ID)
                .rating(UPDATED_RATING)
                .build();
    }

    public DriverRatingResponse getDefaultDriverRatingResponse() {
        return DriverRatingResponse.builder()
                .id(DEFAULT_ID)
                .driverId(DEFAULT_ID)
                .rating(DEFAULT_RATING)
                .build();
    }

    public DriverRatingResponse getUpdatedDriverRatingResponse() {
        return DriverRatingResponse.builder()
                .id(UPDATED_ID)
                .driverId(UPDATED_ID)
                .rating(UPDATED_RATING)
                .build();
    }

    public DriverRatingResponseList getDefaultDriverRatingResponseList() {
        return new DriverRatingResponseList(
                List.of(
                        getDefaultDriverRatingResponse(),
                        getUpdatedDriverRatingResponse()
                )
        );
    }
}
