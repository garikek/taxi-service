package com.software.modsen.driverservice.controller.impl;

import com.software.modsen.driverservice.controller.RideApi;
import com.software.modsen.driverservice.dto.request.DriverRideRequest;
import com.software.modsen.driverservice.service.AvailableRideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/drivers/rides")
public class RideController implements RideApi {
    private final AvailableRideService rideService;

    @Override
    @PostMapping
    public ResponseEntity<Void> acceptRide(@RequestBody DriverRideRequest rideRequest) {
        rideService.acceptRide(rideRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    @PutMapping("/{driverId}")
    public ResponseEntity<Void> finishRide(@PathVariable Long driverId) {
        rideService.finishRide(driverId);
        return ResponseEntity.ok().build();
    }
}

