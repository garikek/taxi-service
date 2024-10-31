package com.software.modsen.passengerservice.service;

import com.software.modsen.passengerservice.dto.PassengerListDTO;
import com.software.modsen.passengerservice.dto.PassengerRequest;
import com.software.modsen.passengerservice.dto.PassengerResponse;

public interface PassengerService {
    PassengerListDTO getPassengers();
    PassengerResponse addPassenger(PassengerRequest passengerRequest);
    PassengerResponse getPassengerById(Long id);
    void deletePassenger(Long id);
    PassengerResponse updatePassenger(Long id, PassengerRequest passengerRequest);
}