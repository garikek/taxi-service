package com.software.modsen.driverservice.service;

import com.software.modsen.driverservice.dto.response.DriverListDto;
import com.software.modsen.driverservice.dto.request.DriverRequest;
import com.software.modsen.driverservice.dto.response.DriverResponse;

public interface DriverService {
    DriverListDto getDrivers();
    DriverResponse addDriver(DriverRequest driverRequest);
    DriverResponse getDriverById(Long id);
    void deleteDriver(Long id);
    DriverResponse updateDriver(Long id, DriverRequest driverRequest);
}
