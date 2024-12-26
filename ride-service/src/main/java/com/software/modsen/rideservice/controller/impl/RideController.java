package com.software.modsen.rideservice.controller.impl;

import com.software.modsen.rideservice.controller.RideApi;
import com.software.modsen.rideservice.dto.response.RideListDto;
import com.software.modsen.rideservice.dto.request.RideRequest;
import com.software.modsen.rideservice.dto.response.RideResponse;
import com.software.modsen.rideservice.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/rides")
public class RideController implements RideApi {
    private final RideService rideService;

    @Override
    @GetMapping
    public ResponseEntity<RideListDto> getRides() {
        return ResponseEntity.ok(rideService.getRides());
    }

    @Override
    @PostMapping
    public ResponseEntity<RideResponse> addRide(@RequestBody RideRequest rideRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rideService.addRide(rideRequest));
    }

    @Override
    @GetMapping("/{id}")
    public RideResponse getRideById(@PathVariable Long id) {
        return rideService.getRideById(id);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deleteRide(@PathVariable Long id) {
        rideService.deleteRide(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<RideResponse> updateRide(
            @PathVariable Long id,
            @RequestBody RideRequest rideRequest
    ) {
        return ResponseEntity.ok(rideService.updateRide(id, rideRequest));
    }
}
