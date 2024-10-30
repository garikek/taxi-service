package com.software.modsen.passengerservice.service.impl;

import com.software.modsen.passengerservice.dto.PassengerListDTO;
import com.software.modsen.passengerservice.dto.PassengerRequest;
import com.software.modsen.passengerservice.dto.PassengerResponse;
import com.software.modsen.passengerservice.exception.*;
import com.software.modsen.passengerservice.mapper.PassengerMapper;
import com.software.modsen.passengerservice.model.Passenger;
import com.software.modsen.passengerservice.repository.PassengerRepository;
import com.software.modsen.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.software.modsen.passengerservice.utility.Constant.PASSENGER_NOT_FOUND_BY_ID;
import static com.software.modsen.passengerservice.utility.Constant.INVALID_EMAIL;
import static com.software.modsen.passengerservice.utility.Constant.INVALID_PHONE_NUMBER;
import static com.software.modsen.passengerservice.utility.Constant.DUPLICATE_EMAIL;
import static com.software.modsen.passengerservice.utility.Constant.DUPLICATE_PHONE_NUMBER;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultPassengerService implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+375\\d{9}$");

    @Override
    public PassengerListDTO getPassengers() {
        return new PassengerListDTO(passengerRepository.findAll().stream()
                .map(passengerMapper::toPassengerDTO)
                .collect(Collectors.toList()));
    }

    @Override
    public PassengerResponse addPassenger(PassengerRequest passengerRequest) {
        Passenger passenger = passengerMapper.toPassengerEntity(passengerRequest);
        validateAddPassenger(passenger);
        Passenger savedPassenger = passengerRepository.save(passenger);
        return passengerMapper.toPassengerDTO(savedPassenger);
    }

    @Override
    public PassengerResponse getPassengerById(Long id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(PASSENGER_NOT_FOUND_BY_ID, id)));
        return passengerMapper.toPassengerDTO(passenger);
    }

    @Override
    public void deletePassenger(Long id) {
        if(!passengerRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format(PASSENGER_NOT_FOUND_BY_ID, id));
        }
        passengerRepository.deleteById(id);
    }

    @Override
    public PassengerResponse updatePassenger(Long id, PassengerRequest passengerRequest) {
        Passenger existingPassenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(PASSENGER_NOT_FOUND_BY_ID, id)));

        passengerMapper.updatePassengerFromDTO(passengerRequest, existingPassenger);
        validateUpdatePassenger(existingPassenger);
        existingPassenger.setUpdatedAt(LocalDateTime.now());
        Passenger updatedPassenger = passengerRepository.save(existingPassenger);
        return passengerMapper.toPassengerDTO(updatedPassenger);
    }

    private void validateAddPassenger (Passenger passenger){
        validateEmail(passenger);
        validatePhoneNumber(passenger);
        checkDuplicateEmail(passenger);
        checkDuplicatePhoneNumber(passenger);
    }

    private void validateUpdatePassenger (Passenger passenger){
        validateEmail(passenger);
        validatePhoneNumber(passenger);
        checkDuplicateEmail(passenger);
        checkDuplicatePhoneNumber(passenger);
    }

    private void validateEmail (Passenger passenger){
        if (!EMAIL_PATTERN.matcher(passenger.getEmail()).matches()) {
            throw new InvalidEmailException(String.format(INVALID_EMAIL, passenger.getEmail()));
        }
    }

    private void validatePhoneNumber (Passenger passenger){
        if (!PHONE_PATTERN.matcher(passenger.getPhoneNumber()).matches()) {
            throw new InvalidPhoneNumberException(String.format(INVALID_PHONE_NUMBER, passenger.getPhoneNumber()));
        }
    }

    private void checkDuplicateEmail (Passenger passenger){
        passengerRepository.findByEmail(passenger.getEmail()).ifPresent(existing -> {
            if (!existing.getId().equals(passenger.getId())) {
                throw new DuplicateEmailException(String.format(DUPLICATE_EMAIL, passenger.getEmail()));
            }
        });
    }

    private void checkDuplicatePhoneNumber (Passenger passenger){
        passengerRepository.findByPhoneNumber(passenger.getPhoneNumber()).ifPresent(existing -> {
            if (!existing.getId().equals(passenger.getId())) {
                throw new DuplicatePhoneNumberException(String.format(DUPLICATE_PHONE_NUMBER, passenger.getPhoneNumber()));
            }
        });
    }

}
