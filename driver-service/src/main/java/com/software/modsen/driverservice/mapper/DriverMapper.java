package com.software.modsen.driverservice.mapper;

import com.software.modsen.driverservice.dto.DriverRequest;
import com.software.modsen.driverservice.dto.DriverResponse;
import com.software.modsen.driverservice.model.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DriverMapper {
    DriverResponse toDriverDto(Driver driver);
    Driver toDriverEntity(DriverRequest driverRequest);
    void updateDriverFromDto(DriverRequest driverRequest, @MappingTarget Driver driver);
}