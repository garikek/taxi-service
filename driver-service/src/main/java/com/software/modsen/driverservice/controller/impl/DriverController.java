package com.software.modsen.driverservice.controller.impl;

import com.software.modsen.driverservice.controller.DriverApi;
import com.software.modsen.driverservice.dto.response.DriverListDto;
import com.software.modsen.driverservice.dto.request.DriverRequest;
import com.software.modsen.driverservice.dto.response.DriverResponse;
import com.software.modsen.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class DriverController implements DriverApi {
    private final DriverService driverService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ResponseEntity<DriverListDto> getDrivers() {
        return ResponseEntity.ok(driverService.getDrivers());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ResponseEntity<DriverResponse> addDriver(DriverRequest driverRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(driverService.addDriver(driverRequest));
    }

    @PreAuthorize("hasAnyRole('ROLE_PASSENGER','ROLE_ADMIN')")
    @Override
    public DriverResponse getDriverById(Long id) {
        return driverService.getDriverById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ResponseEntity<Void>  deleteDriver(Long id) {
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_DRIVER','ROLE_ADMIN')")
    @Override
    public ResponseEntity<DriverResponse> updateDriver(Long id, DriverRequest driverRequest) {
        return ResponseEntity.ok(driverService.updateDriver(id, driverRequest));
    }
}
