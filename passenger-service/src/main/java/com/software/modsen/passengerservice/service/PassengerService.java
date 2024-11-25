package com.software.modsen.passengerservice.service;

import com.software.modsen.passengerservice.dto.response.PassengerListDTO;
import com.software.modsen.passengerservice.dto.request.PassengerRequest;
import com.software.modsen.passengerservice.dto.response.PassengerResponse;

public interface PassengerService {
    PassengerListDTO getPassengers();
    PassengerResponse addPassenger(PassengerRequest passengerRequest);
    PassengerResponse getPassengerById(Long id);
    void deletePassenger(Long id);
    PassengerResponse updatePassenger(Long id, PassengerRequest passengerRequest);
}