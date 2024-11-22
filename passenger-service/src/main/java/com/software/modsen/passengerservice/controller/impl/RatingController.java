package com.software.modsen.passengerservice.controller.impl;

import com.software.modsen.passengerservice.controller.RatingApi;
import com.software.modsen.passengerservice.dto.request.RatingRequest;
import com.software.modsen.passengerservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/passengers/ratings")
public class RatingController implements RatingApi {
    private final RatingService ratingService;

    @Override
    @PostMapping
    public ResponseEntity<Void> addRating(@RequestBody RatingRequest ratingRequest) {
        ratingService.addRating(ratingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @PutMapping("/{passengerId}")
    public ResponseEntity<Void> updateRating(@RequestBody RatingRequest ratingRequest) {
        ratingService.updateRating(ratingRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    @DeleteMapping("/{passengerId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long passengerId) {
        ratingService.deleteRating(passengerId);
        return ResponseEntity.noContent().build();
    }
}
