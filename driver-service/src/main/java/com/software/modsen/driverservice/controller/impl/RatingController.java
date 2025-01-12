package com.software.modsen.driverservice.controller.impl;

import com.software.modsen.driverservice.controller.RatingApi;
import com.software.modsen.driverservice.dto.request.RatingRequest;
import com.software.modsen.driverservice.dto.response.DriverRatingResponseList;
import com.software.modsen.driverservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasAnyRole('ROLE_DRIVER','ROLE_ADMIN')")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/drivers/ratings")
public class RatingController implements RatingApi {
    private final RatingService ratingService;

    @Override
    @PostMapping
    public ResponseEntity<Void> addRating(@RequestBody RatingRequest ratingRequest) {
        ratingService.addRating(ratingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @PutMapping("/{driverId}")
    public ResponseEntity<Void> updateRating(@RequestBody RatingRequest ratingRequest) {
        ratingService.updateRating(ratingRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    @DeleteMapping("/{driverId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long driverId) {
        ratingService.deleteRating(driverId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/{driverId}")
    public ResponseEntity<DriverRatingResponseList> getDriverRatings(@PathVariable Long driverId) {
        return ResponseEntity.ok(ratingService.getDriverRatings(driverId));
    }
}
