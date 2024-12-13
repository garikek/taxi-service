package com.software.modsen.passengerservice.service;

import com.software.modsen.passengerservice.dto.request.PassengerRequest;
import com.software.modsen.passengerservice.dto.response.PassengerResponse;
import com.software.modsen.passengerservice.exception.*;
import com.software.modsen.passengerservice.mapper.PassengerMapper;
import com.software.modsen.passengerservice.model.Passenger;
import com.software.modsen.passengerservice.repository.PassengerRepository;
import com.software.modsen.passengerservice.service.impl.DefaultPassengerService;
import com.software.modsen.passengerservice.util.PassengerTestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.software.modsen.passengerservice.util.PassengerTestUtil.*;

@ExtendWith(MockitoExtension.class)
class DefaultPassengerServiceTest {
    @Mock
    private PassengerRepository passengerRepository;
    @Mock
    private PassengerMapper passengerMapper;
    @InjectMocks
    private DefaultPassengerService passengerService;

    @Test
    void testGetPassengerById_Success() {
        Passenger passenger = getDefaultPassenger();

        when(passengerRepository.findById(DEFAULT_PASSENGER_ID)).thenReturn(Optional.of(passenger));
        when(passengerMapper.toPassengerDTO(passenger)).thenReturn(getDefaultPassengerResponse());

        PassengerResponse response = passengerService.getPassengerById(DEFAULT_PASSENGER_ID);

        assertEquals(DEFAULT_NAME, response.getName());
        verify(passengerRepository, times(1)).findById(DEFAULT_PASSENGER_ID);
    }

    @Test
    void testGetPassengerById_NotFound() {
        when(passengerRepository.findById(DEFAULT_PASSENGER_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> passengerService.getPassengerById(DEFAULT_PASSENGER_ID));
    }

    @Test
    void testAddPassenger_Success() {
        PassengerRequest request = getDefaultPassengerRequest();
        Passenger passenger = getDefaultPassenger();
        Passenger savedPassenger = PassengerTestUtil.getDefaultPassenger();

        when(passengerMapper.toPassengerEntity(request)).thenReturn(passenger);
        when(passengerRepository.save(passenger)).thenReturn(savedPassenger);
        when(passengerMapper.toPassengerDTO(savedPassenger)).thenReturn(getDefaultPassengerResponse());

        PassengerResponse response = passengerService.addPassenger(request);

        assertEquals(DEFAULT_NAME, response.getName());
        verify(passengerRepository, times(1)).save(passenger);
    }

    @Test
    void testAddPassenger_InvalidEmail() {
        PassengerRequest request = getDefaultPassengerRequestWithInvalidEmail();

        when(passengerMapper.toPassengerEntity(request)).thenReturn(getPassengerWithInvalidEmail());

        InvalidEmailException exception = assertThrows(InvalidEmailException.class,
                () -> passengerService.addPassenger(request));
        assertEquals("Invalid email: " + INVALID_EMAIL, exception.getMessage());
    }

    @Test
    void testAddPassenger_InvalidPhoneNumber() {
        PassengerRequest request = getDefaultPassengerRequestWithInvalidPhoneNumber();

        when(passengerMapper.toPassengerEntity(request)).thenReturn(getPassengerWithInvalidPhoneNumber());

        InvalidPhoneNumberException exception = assertThrows(InvalidPhoneNumberException.class,
                () -> passengerService.addPassenger(request));
        assertEquals("Invalid phone number: " + INVALID_PHONE_NUMBER, exception.getMessage());
    }

    @Test
    void testAddPassenger_DuplicateEmail() {
        PassengerRequest request = getDefaultPassengerRequest();

        when(passengerMapper.toPassengerEntity(request)).thenReturn(getDefaultPassenger());
        when(passengerRepository.findByEmail(DEFAULT_EMAIL)).thenReturn(Optional.of(getPassengerWithEmailOnly()));

        DuplicateEmailException exception = assertThrows(DuplicateEmailException.class,
                () -> passengerService.addPassenger(request));
        assertEquals("Email " + DEFAULT_EMAIL + " exists already", exception.getMessage());
    }

    @Test
    void testAddPassenger_DuplicatePhoneNumber() {
        PassengerRequest request = getDefaultPassengerRequest();

        when(passengerMapper.toPassengerEntity(request)).thenReturn(getDefaultPassenger());
        when(passengerRepository.findByPhoneNumber(DEFAULT_PHONE_NUMBER)).thenReturn(Optional.of(getPassengerWithPhoneNumberOnly()));

        DuplicatePhoneNumberException exception = assertThrows(DuplicatePhoneNumberException.class,
                () -> passengerService.addPassenger(request));
        assertEquals("Phone number " + DEFAULT_PHONE_NUMBER + " exists already", exception.getMessage());
    }

    @Test
    void testUpdatePassenger_Success() {
        Passenger existingPassenger = getDefaultPassenger();
        PassengerRequest updateRequest = getUpdatedPassengerRequest();
        Passenger updatedPassenger = PassengerTestUtil.getUpdatedPassenger();

        when(passengerRepository.findById(DEFAULT_PASSENGER_ID)).thenReturn(Optional.of(existingPassenger));
        doNothing().when(passengerMapper).updatePassengerFromDTO(updateRequest, existingPassenger);
        when(passengerRepository.save(existingPassenger)).thenReturn(updatedPassenger);
        when(passengerMapper.toPassengerDTO(updatedPassenger)).thenReturn(getUpdatedPassengerResponse());

        PassengerResponse response = passengerService.updatePassenger(DEFAULT_PASSENGER_ID, updateRequest);

        assertEquals(UPDATED_NAME, response.getName());
        verify(passengerRepository, times(1)).save(existingPassenger);
    }

    @Test
    void testDeletePassenger_Success() {
        when(passengerRepository.existsById(DEFAULT_PASSENGER_ID)).thenReturn(true);

        passengerService.deletePassenger(DEFAULT_PASSENGER_ID);

        verify(passengerRepository, times(1)).deleteById(DEFAULT_PASSENGER_ID);
    }

    @Test
    void testDeletePassenger_NotFound() {
        when(passengerRepository.existsById(DEFAULT_PASSENGER_ID)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> passengerService.deletePassenger(DEFAULT_PASSENGER_ID));
    }
}
