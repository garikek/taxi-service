package com.software.modsen.ratingservice.controller.impl;

import com.software.modsen.ratingservice.controller.PassengerRatingApi;
import com.software.modsen.ratingservice.dto.request.PassengerRatingRequest;
import com.software.modsen.ratingservice.dto.response.PassengerRatingResponse;
import com.software.modsen.ratingservice.dto.response.PassengerRatingResponseList;
import com.software.modsen.ratingservice.service.PassengerRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PassengerRatingController implements PassengerRatingApi {
    private final PassengerRatingService passengerRatingService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ResponseEntity<PassengerRatingResponseList> getPassengerRatings() {
        return ResponseEntity.ok(passengerRatingService.getPassengerRatings());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ResponseEntity<PassengerRatingResponse> addPassengerRating(PassengerRatingRequest passengerRatingRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(passengerRatingService.addPassengerRating(passengerRatingRequest));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public PassengerRatingResponse getPassengerRatingById(Long id) {
        return passengerRatingService.getPassengerRatingById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ResponseEntity<Void>  deletePassengerRating(Long id) {
        passengerRatingService.deletePassengerRating(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ResponseEntity<PassengerRatingResponse> updatePassengerRating(Long id, PassengerRatingRequest passengerRatingRequest) {
        return ResponseEntity.ok(passengerRatingService.updatePassengerRating(id, passengerRatingRequest));
    }
}
