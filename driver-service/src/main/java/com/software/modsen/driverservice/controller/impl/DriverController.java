package com.software.modsen.driverservice.controller.impl;

import com.software.modsen.driverservice.controller.DriverApi;
import com.software.modsen.driverservice.dto.DriverListDto;
import com.software.modsen.driverservice.dto.DriverRequest;
import com.software.modsen.driverservice.dto.DriverResponse;
import com.software.modsen.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class DriverController implements DriverApi {
    private final DriverService driverService;

    @Override
    public ResponseEntity<DriverListDto> getDrivers() {
        return ResponseEntity.ok(driverService.getDrivers());
    }

    @Override
    public ResponseEntity<DriverResponse> addDriver(DriverRequest driverRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(driverService.addDriver(driverRequest));
    }

    @Override
    public DriverResponse getDriverById(Long id) {
        return driverService.getDriverById(id);
    }

    @Override
    public ResponseEntity<Void>  deleteDriver(Long id) {
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<DriverResponse> updateDriver(Long id, DriverRequest driverRequest) {
        return ResponseEntity.ok(driverService.updateDriver(id, driverRequest));
    }
}