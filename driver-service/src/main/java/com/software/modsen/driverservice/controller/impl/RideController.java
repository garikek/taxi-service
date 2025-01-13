package com.software.modsen.driverservice.controller.impl;

import com.software.modsen.driverservice.controller.RideApi;
import com.software.modsen.driverservice.dto.request.DriverRideRequest;
import com.software.modsen.driverservice.dto.response.AvailableRideListDto;
import com.software.modsen.driverservice.dto.response.RideListDto;
import com.software.modsen.driverservice.service.AvailableRideService;
import com.software.modsen.driverservice.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasAnyRole('ROLE_DRIVER','ROLE_ADMIN')")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/drivers/rides")
public class RideController implements RideApi {
    private final AvailableRideService availableRideService;
    private final RideService rideService;

    @Override
    @GetMapping
    public ResponseEntity<AvailableRideListDto> getAvailableRides() {
        return ResponseEntity.ok(availableRideService.getAvailableRides());
    }

    @Override
    @PostMapping
    public ResponseEntity<Void> acceptRide(@RequestBody DriverRideRequest rideRequest) {
        availableRideService.acceptRide(rideRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    @PutMapping("/{driverId}")
    public ResponseEntity<Void> finishRide(@PathVariable Long driverId) {
        availableRideService.finishRide(driverId);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/history/{driverId}")
    public ResponseEntity<RideListDto> getRideHistory(@PathVariable Long driverId) {
        return ResponseEntity.ok(rideService.getRideHistory(driverId));
    }
}
