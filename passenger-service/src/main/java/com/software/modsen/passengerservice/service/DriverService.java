package com.software.modsen.passengerservice.service;

import com.software.modsen.passengerservice.dto.response.DriverResponse;

public interface DriverService {
    DriverResponse getDriverProfile(Long driverId);
}
