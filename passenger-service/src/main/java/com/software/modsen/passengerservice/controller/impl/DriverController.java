package com.software.modsen.passengerservice.controller.impl;

import com.software.modsen.passengerservice.controller.DriverApi;
import com.software.modsen.passengerservice.dto.response.DriverResponse;
import com.software.modsen.passengerservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/passengers/drivers")
public class DriverController implements DriverApi {
    private final DriverService driverService;

    @PreAuthorize("hasAnyRole('ROLE_PASSENGER','ROLE_ADMIN')")
    @Override
    @GetMapping("/{driverId}")
    public ResponseEntity<DriverResponse> getDriverProfile(Long driverId) {
        return ResponseEntity.ok(driverService.getDriverProfile(driverId));
    }
}
