package com.software.modsen.driverservice.service.impl;

import com.software.modsen.driverservice.client.RideFeignClient;
import com.software.modsen.driverservice.dto.response.RideListDto;
import com.software.modsen.driverservice.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {
    private final RideFeignClient rideFeignClient;

    @Override
    public RideListDto getRideHistory(Long driverId) {
        return rideFeignClient.getRideHistory(driverId);
    }
}
