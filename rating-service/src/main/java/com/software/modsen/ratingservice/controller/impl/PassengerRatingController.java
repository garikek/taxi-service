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
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/ratings/passengers")
public class PassengerRatingController implements PassengerRatingApi {
    private final PassengerRatingService passengerRatingService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @GetMapping
    public ResponseEntity<PassengerRatingResponseList> getPassengerRatings() {
        return ResponseEntity.ok(passengerRatingService.getPassengerRatings());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @PostMapping
    public ResponseEntity<PassengerRatingResponse> addPassengerRating(
            @RequestBody PassengerRatingRequest passengerRatingRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(passengerRatingService.addPassengerRating(passengerRatingRequest));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @GetMapping("/{id}")
    public PassengerRatingResponse getPassengerRatingById(@PathVariable Long id) {
        return passengerRatingService.getPassengerRatingById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deletePassengerRating(@PathVariable Long id) {
        passengerRatingService.deletePassengerRating(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<PassengerRatingResponse> updatePassengerRating(
            @PathVariable Long id,
            @RequestBody PassengerRatingRequest passengerRatingRequest
    ) {
        return ResponseEntity.ok(passengerRatingService.updatePassengerRating(id, passengerRatingRequest));
    }

    @PreAuthorize("hasAnyRole('ROLE_PASSENGER','ROLE_ADMIN')")
    @Override
    @GetMapping("/list/{passengerId}")
    public ResponseEntity<PassengerRatingResponseList> getPassengerRatingsByPassengerId(@PathVariable Long passengerId) {
        return ResponseEntity.ok(passengerRatingService.getPassengerRatingsByPassengerId(passengerId));
    }
}
