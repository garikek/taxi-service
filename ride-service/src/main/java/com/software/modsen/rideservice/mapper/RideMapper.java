package com.software.modsen.rideservice.mapper;

import com.software.modsen.rideservice.dto.RideRequest;
import com.software.modsen.rideservice.dto.RideResponse;
import com.software.modsen.rideservice.model.Ride;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RideMapper {
    RideResponse toRideDto(Ride ride);
    Ride toRideEntity(RideRequest rideRequest);
    void updateRideFromDto(RideRequest rideRequest, @MappingTarget Ride ride);
}