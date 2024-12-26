package com.software.modsen.driverservice.mapper;

import com.software.modsen.driverservice.dto.request.AvailableRideRequest;
import com.software.modsen.driverservice.dto.response.AvailableRideResponse;
import com.software.modsen.driverservice.model.AvailableRide;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AvailableRideMapper {
    AvailableRideResponse toDto(AvailableRide availableRide);
    AvailableRide toEntity(AvailableRideRequest availableRideRequest);
}
