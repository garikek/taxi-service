package com.software.modsen.rideservice.service;

import com.software.modsen.rideservice.dto.request.RideRequest;
import com.software.modsen.rideservice.dto.response.RideResponse;
import com.software.modsen.rideservice.exception.InvalidResourceException;
import com.software.modsen.rideservice.exception.ResourceNotFoundException;
import com.software.modsen.rideservice.mapper.RideMapper;
import com.software.modsen.rideservice.model.Ride;
import com.software.modsen.rideservice.repository.RideRepository;
import com.software.modsen.rideservice.service.impl.DefaultRideService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.software.modsen.rideservice.util.RideTestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultRideServiceTest {
    @Mock
    private RideRepository rideRepository;
    @Mock
    private RideMapper rideMapper;
    @InjectMocks
    private DefaultRideService rideService;

    @Test
    void testGetRideById_Success() {
        Ride ride = getDefaultRide();

        when(rideRepository.findById(DEFAULT_ID)).thenReturn(Optional.of(ride));
        when(rideMapper.toRideDto(ride)).thenReturn(getDefaultRideResponse());

        RideResponse response = rideService.getRideById(DEFAULT_ID);

        assertEquals(DEFAULT_PICKUP_ADDRESS, response.getPickupAddress());
        verify(rideRepository, times(1)).findById(DEFAULT_ID);
    }

    @Test
    void testGetRideById_NotFound() {
        when(rideRepository.findById(DEFAULT_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> rideService.getRideById(DEFAULT_ID));
    }

    @Test
    void testAddRide_Success() {
        RideRequest request = getDefaultRideRequest();
        Ride ride = getDefaultRide();
        Ride savedRide = getDefaultRide();

        when(rideMapper.toRideEntity(request)).thenReturn(ride);
        when(rideRepository.save(ride)).thenReturn(savedRide);
        when(rideMapper.toRideDto(savedRide)).thenReturn(getDefaultRideResponse());

        RideResponse response = rideService.addRide(request);

        assertEquals(DEFAULT_PICKUP_ADDRESS, response.getPickupAddress());
        verify(rideRepository, times(1)).save(ride);
    }

    @Test
    void testAddRide_InvalidPickupAddress() {
        RideRequest request = getDefaultRideRequestWithInvalidPickupAddress();

        when(rideMapper.toRideEntity(request)).thenReturn(getDefaultRideWithInvalidPickupAddress());

        InvalidResourceException exception = assertThrows(InvalidResourceException.class,
                () -> rideService.addRide(request));
        assertEquals("Invalid address: " + INVALID_PICKUP_ADDRESS, exception.getMessage());
    }

    @Test
    void testAddRide_InvalidDestinationAddress() {
        RideRequest request = getDefaultRideRequestWithInvalidDestinationAddress();

        when(rideMapper.toRideEntity(request)).thenReturn(getDefaultRideWithInvalidDestinationAddress());

        InvalidResourceException exception = assertThrows(InvalidResourceException.class,
                () -> rideService.addRide(request));
        assertEquals("Invalid address: " + INVALID_DESTINATION_ADDRESS, exception.getMessage());
    }

    @Test
    void testUpdateRide_Success() {
        Ride existingRide = getDefaultRide();
        RideRequest updateRequest = getUpdatedRideRequest();
        Ride updatedRide = getUpdatedRide();

        when(rideRepository.findById(DEFAULT_ID)).thenReturn(Optional.of(existingRide));
        doNothing().when(rideMapper).updateRideFromDto(updateRequest, existingRide);
        when(rideRepository.save(existingRide)).thenReturn(updatedRide);
        when(rideMapper.toRideDto(updatedRide)).thenReturn(getUpdatedRideResponse());

        RideResponse response = rideService.updateRide(DEFAULT_ID, updateRequest);

        assertEquals(UPDATED_PICKUP_ADDRESS, response.getPickupAddress());
        verify(rideRepository, times(1)).save(existingRide);
    }

    @Test
    void testDeleteRide_Success() {
        when(rideRepository.existsById(DEFAULT_ID)).thenReturn(true);

        rideService.deleteRide(DEFAULT_ID);

        verify(rideRepository, times(1)).deleteById(DEFAULT_ID);
    }

    @Test
    void testDeleteRide_NotFound() {
        when(rideRepository.existsById(DEFAULT_ID)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> rideService.deleteRide(DEFAULT_ID));
    }
}
