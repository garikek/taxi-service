package com.software.modsen.driverservice.service;

import com.software.modsen.driverservice.dto.DriverListDto;
import com.software.modsen.driverservice.dto.DriverRequest;
import com.software.modsen.driverservice.dto.DriverResponse;

public interface DriverService {
    DriverListDto getDrivers();
    DriverResponse addDriver(DriverRequest driverRequest);
    DriverResponse getDriverById(Long id);
    void deleteDriver(Long id);
    DriverResponse updateDriver(Long id, DriverRequest driverRequest);
}
