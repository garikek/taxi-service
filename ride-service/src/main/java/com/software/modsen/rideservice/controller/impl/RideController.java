package com.software.modsen.rideservice.controller.impl;

import com.software.modsen.rideservice.controller.RideApi;
import com.software.modsen.rideservice.dto.response.RideListDto;
import com.software.modsen.rideservice.dto.request.RideRequest;
import com.software.modsen.rideservice.dto.response.RideResponse;
import com.software.modsen.rideservice.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/rides")
public class RideController implements RideApi {
    private final RideService rideService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @GetMapping
    public ResponseEntity<RideListDto> getRides() {
        return ResponseEntity.ok(rideService.getRides());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @PostMapping
    public ResponseEntity<RideResponse> addRide(@RequestBody RideRequest rideRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rideService.addRide(rideRequest));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @GetMapping("/{id}")
    public RideResponse getRideById(@PathVariable Long id) {
        return rideService.getRideById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRide(@PathVariable Long id) {
        rideService.deleteRide(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<RideResponse> updateRide(
            @PathVariable Long id,
            @RequestBody RideRequest rideRequest
    ) {
        return ResponseEntity.ok(rideService.updateRide(id, rideRequest));
    }

    @PreAuthorize("hasAnyRole('ROLE_DRIVER','ROLE_ADMIN')")
    @Override
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<RideListDto> getCompletedRidesByDriverId(@PathVariable Long driverId) {
        return ResponseEntity.ok(rideService.getCompletedRidesByDriverId(driverId));
    }

    @PreAuthorize("hasAnyRole('ROLE_PASSENGER','ROLE_ADMIN')")
    @Override
    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<RideListDto> getCompletedRidesByPassengerId(@PathVariable Long passengerId) {
        return ResponseEntity.ok(rideService.getCompletedRidesByPassengerId(passengerId));
    }

    @PreAuthorize("hasAnyRole('ROLE_PASSENGER','ROLE_ADMIN')")
    @Override
    @GetMapping("/price/{id}")
    public Double getEstimatedRidePrice(@PathVariable Long id) {
        return rideService.getEstimatedRidePrice(id);
    }
}
