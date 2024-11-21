package com.software.modsen.ratingservice.controller.impl;

import com.software.modsen.ratingservice.controller.DriverRatingApi;
import com.software.modsen.ratingservice.dto.request.DriverRatingRequest;
import com.software.modsen.ratingservice.dto.response.DriverRatingResponse;
import com.software.modsen.ratingservice.dto.response.DriverRatingResponseList;
import com.software.modsen.ratingservice.service.DriverRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DriverRatingController implements DriverRatingApi {
    private final DriverRatingService driverRatingService;

    @Override
    public ResponseEntity<DriverRatingResponseList> getDriverRatings() {
        return ResponseEntity.ok(driverRatingService.getDriverRatings());
    }

    @Override
    public ResponseEntity<DriverRatingResponse> addDriverRating(DriverRatingRequest driverRatingRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(driverRatingService.addDriverRating(driverRatingRequest));
    }

    @Override
    public DriverRatingResponse getDriverRatingById(Long id) {
        return driverRatingService.getDriverRatingById(id);
    }

    @Override
    public ResponseEntity<Void>  deleteDriverRating(Long id) {
        driverRatingService.deleteDriverRating(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<DriverRatingResponse> updateDriverRating(Long id, DriverRatingRequest driverRatingRequest) {
        return ResponseEntity.ok(driverRatingService.updateDriverRating(id, driverRatingRequest));
    }
}
