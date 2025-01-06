package com.software.modsen.driverservice.controller.impl;

import com.software.modsen.driverservice.controller.RideApi;
import com.software.modsen.driverservice.dto.request.DriverRideRequest;
import com.software.modsen.driverservice.dto.response.AvailableRideListDto;
import com.software.modsen.driverservice.service.AvailableRideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/drivers/rides")
public class RideController implements RideApi {
    private final AvailableRideService rideService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @GetMapping
    public ResponseEntity<AvailableRideListDto> getAvailableRides() {
        return ResponseEntity.ok(rideService.getAvailableRides());
    }

    @PreAuthorize("hasAnyRole('ROLE_DRIVER','ROLE_ADMIN')")
    @Override
    @PostMapping
    public ResponseEntity<Void> acceptRide(@RequestBody DriverRideRequest rideRequest) {
        rideService.acceptRide(rideRequest);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_DRIVER','ROLE_ADMIN')")
    @Override
    @PutMapping("/{driverId}")
    public ResponseEntity<Void> finishRide(@PathVariable Long driverId) {
        rideService.finishRide(driverId);
        return ResponseEntity.ok().build();
    }
}

