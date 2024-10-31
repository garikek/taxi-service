package com.software.modsen.passengerservice.mapper;

import com.software.modsen.passengerservice.dto.PassengerRequest;
import com.software.modsen.passengerservice.dto.PassengerResponse;
import com.software.modsen.passengerservice.model.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PassengerMapper {
    PassengerResponse toPassengerDTO(Passenger passenger);
    Passenger toPassengerEntity(PassengerRequest passengerRequest);
    void updatePassengerFromDTO(PassengerRequest passengerRequest, @MappingTarget Passenger passenger);
}