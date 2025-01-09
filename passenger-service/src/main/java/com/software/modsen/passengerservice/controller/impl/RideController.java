package com.software.modsen.passengerservice.controller.impl;

import com.software.modsen.passengerservice.controller.RideApi;
import com.software.modsen.passengerservice.dto.request.RideRequest;
import com.software.modsen.passengerservice.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/passengers/rides")
public class RideController implements RideApi {
    private final RideService rideService;

    @PreAuthorize("hasAnyRole('ROLE_PASSENGER','ROLE_ADMIN')")
    @Override
    @PostMapping
    public ResponseEntity<Void> requestRide(@RequestBody RideRequest rideRequest) {
        rideService.requestRide(rideRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAnyRole('ROLE_PASSENGER','ROLE_ADMIN')")
    @Override
    @PutMapping("/{passengerId}")
    public ResponseEntity<Void> cancelRide(@PathVariable Long passengerId) {
        rideService.cancelRide(passengerId);
        return ResponseEntity.ok().build();
    }
}
