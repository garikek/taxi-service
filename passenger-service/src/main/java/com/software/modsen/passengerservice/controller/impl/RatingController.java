package com.software.modsen.passengerservice.controller.impl;

import com.software.modsen.passengerservice.controller.RatingApi;
import com.software.modsen.passengerservice.dto.request.RatingRequest;
import com.software.modsen.passengerservice.dto.response.PassengerRatingResponseList;
import com.software.modsen.passengerservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/passengers/ratings")
public class RatingController implements RatingApi {
    private final RatingService ratingService;

    @PreAuthorize("hasAnyRole('ROLE_PASSENGER','ROLE_ADMIN')")
    @Override
    @PostMapping
    public ResponseEntity<Void> addRating(@RequestBody RatingRequest ratingRequest) {
        ratingService.addRating(ratingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAnyRole('ROLE_PASSENGER','ROLE_ADMIN')")
    @Override
    @PutMapping("/{passengerId}")
    public ResponseEntity<Void> updateRating(@RequestBody RatingRequest ratingRequest) {
        ratingService.updateRating(ratingRequest);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_PASSENGER','ROLE_ADMIN')")
    @Override
    @DeleteMapping("/{passengerId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long passengerId) {
        ratingService.deleteRating(passengerId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_PASSENGER','ROLE_ADMIN')")
    @Override
    @GetMapping("/{passengerId}")
    public ResponseEntity<PassengerRatingResponseList> getPassengerRatings(Long passengerId) {
        return ResponseEntity.ok(ratingService.getPassengerRatings(passengerId));
    }
}
