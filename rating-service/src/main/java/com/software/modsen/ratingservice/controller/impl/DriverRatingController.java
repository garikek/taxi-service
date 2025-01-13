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
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/ratings/drivers")
public class DriverRatingController implements DriverRatingApi {
    private final DriverRatingService driverRatingService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @GetMapping
    public ResponseEntity<DriverRatingResponseList> getDriverRatings() {
        return ResponseEntity.ok(driverRatingService.getDriverRatings());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @PostMapping
    public ResponseEntity<DriverRatingResponse> addDriverRating(@RequestBody DriverRatingRequest driverRatingRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(driverRatingService.addDriverRating(driverRatingRequest));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @GetMapping("/{id}")
    public DriverRatingResponse getDriverRatingById(@PathVariable Long id) {
        return driverRatingService.getDriverRatingById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deleteDriverRating(@PathVariable Long id) {
        driverRatingService.deleteDriverRating(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<DriverRatingResponse> updateDriverRating(@PathVariable Long id, @RequestBody DriverRatingRequest driverRatingRequest) {
        return ResponseEntity.ok(driverRatingService.updateDriverRating(id, driverRatingRequest));
    }

    @PreAuthorize("hasAnyRole('ROLE_DRIVER','ROLE_ADMIN')")
    @Override
    @GetMapping("/list/{driverId}")
    public ResponseEntity<DriverRatingResponseList> getDriverRatingsByDriverId(@PathVariable Long driverId) {
        return ResponseEntity.ok(driverRatingService.getDriverRatingsByDriverId(driverId));
    }
}
