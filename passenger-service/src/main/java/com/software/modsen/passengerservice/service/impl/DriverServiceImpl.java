package com.software.modsen.passengerservice.service.impl;

import com.software.modsen.passengerservice.client.DriverFeignClient;
import com.software.modsen.passengerservice.dto.response.DriverResponse;
import com.software.modsen.passengerservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverServiceImpl implements DriverService {
    private final DriverFeignClient driverFeignClient;

    @Override
    public DriverResponse getDriverProfile(Long driverId) {
        return driverFeignClient.getDriverProfile(driverId);
    }
}
