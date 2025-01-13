package com.software.modsen.passengerservice.controller.impl;

import com.software.modsen.passengerservice.controller.RideApi;
import com.software.modsen.passengerservice.dto.request.RideRequest;
import com.software.modsen.passengerservice.dto.response.RideListDto;
import com.software.modsen.passengerservice.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasAnyRole('ROLE_PASSENGER','ROLE_ADMIN')")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/passengers/rides")
public class RideController implements RideApi {
    private final RideService rideService;

    @Override
    @PostMapping
    public ResponseEntity<Void> requestRide(@RequestBody RideRequest rideRequest) {
        rideService.requestRide(rideRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @PutMapping("/{passengerId}")
    public ResponseEntity<Void> cancelRide(@PathVariable Long passengerId) {
        rideService.cancelRide(passengerId);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/price/{rideId}")
    public ResponseEntity<Double> getEstimatedRidePrice(@PathVariable Long rideId) {
        return ResponseEntity.ok(rideService.getEstimatedRidePrice(rideId));
    }

    @Override
    @GetMapping("/history/{passengerId}")
    public ResponseEntity<RideListDto> getRideHistory(@PathVariable Long passengerId) {
        return ResponseEntity.ok(rideService.getRideHistory(passengerId));
    }
}
