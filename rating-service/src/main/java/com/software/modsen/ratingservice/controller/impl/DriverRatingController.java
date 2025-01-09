package com.software.modsen.ratingservice.controller.impl;

import com.software.modsen.ratingservice.controller.DriverRatingApi;
import com.software.modsen.ratingservice.dto.request.DriverRatingRequest;
import com.software.modsen.ratingservice.dto.response.DriverRatingResponse;
import com.software.modsen.ratingservice.dto.response.DriverRatingResponseList;
import com.software.modsen.ratingservice.service.DriverRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DriverRatingController implements DriverRatingApi {
    private final DriverRatingService driverRatingService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ResponseEntity<DriverRatingResponseList> getDriverRatings() {
        return ResponseEntity.ok(driverRatingService.getDriverRatings());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ResponseEntity<DriverRatingResponse> addDriverRating(DriverRatingRequest driverRatingRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(driverRatingService.addDriverRating(driverRatingRequest));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public DriverRatingResponse getDriverRatingById(Long id) {
        return driverRatingService.getDriverRatingById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ResponseEntity<Void>  deleteDriverRating(Long id) {
        driverRatingService.deleteDriverRating(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ResponseEntity<DriverRatingResponse> updateDriverRating(Long id, DriverRatingRequest driverRatingRequest) {
        return ResponseEntity.ok(driverRatingService.updateDriverRating(id, driverRatingRequest));
    }
}
