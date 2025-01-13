package com.software.modsen.driverservice.service;

import com.software.modsen.driverservice.dto.response.RideListDto;

public interface RideService {
    RideListDto getRideHistory(Long driverId);
}
