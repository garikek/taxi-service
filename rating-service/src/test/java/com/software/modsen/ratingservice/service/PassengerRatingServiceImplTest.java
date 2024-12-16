package com.software.modsen.ratingservice.service;

import com.software.modsen.ratingservice.dto.request.PassengerRatingRequest;
import com.software.modsen.ratingservice.dto.response.PassengerRatingResponse;
import com.software.modsen.ratingservice.exception.DuplicateResourceException;
import com.software.modsen.ratingservice.exception.ResourceNotFoundException;
import com.software.modsen.ratingservice.mapper.PassengerRatingMapper;
import com.software.modsen.ratingservice.model.PassengerRating;
import com.software.modsen.ratingservice.repository.PassengerRatingRepository;
import com.software.modsen.ratingservice.service.impl.PassengerRatingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.software.modsen.ratingservice.util.PassengerRatingTestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassengerRatingServiceImplTest {
    @Mock
    private PassengerRatingRepository passengerRatingRepository;
    @Mock
    private PassengerRatingMapper passengerRatingMapper;
    @InjectMocks
    private PassengerRatingServiceImpl passengerRatingService;

    @Test
    void testGetPassengerRatingById_Success() {
        PassengerRating passengerRating = getDefaultPassengerRating();

        when(passengerRatingRepository.findById(DEFAULT_ID)).thenReturn(Optional.of(passengerRating));
        when(passengerRatingMapper.toPassengerRatingDto(passengerRating)).thenReturn(getDefaultPassengerRatingResponse());

        PassengerRatingResponse response = passengerRatingService.getPassengerRatingById(DEFAULT_ID);

        assertEquals(DEFAULT_RATING, response.getRating());
        verify(passengerRatingRepository, times(1)).findById(DEFAULT_ID);
    }

    @Test
    void testGetPassengerRatingById_NotFound() {
        when(passengerRatingRepository.findById(DEFAULT_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> passengerRatingService.getPassengerRatingById(DEFAULT_ID));
    }

    @Test
    void testAddPassengerRating_Success() {
        PassengerRatingRequest request = getDefaultPassengerRatingRequest();
        PassengerRating passengerRating = getDefaultPassengerRating();
        PassengerRating savedPassengerRating = getDefaultPassengerRating();

        when(passengerRatingMapper.toPassengerRatingEntity(request)).thenReturn(passengerRating);
        when(passengerRatingRepository.save(passengerRating)).thenReturn(savedPassengerRating);
        when(passengerRatingMapper.toPassengerRatingDto(savedPassengerRating)).thenReturn(getDefaultPassengerRatingResponse());

        PassengerRatingResponse response = passengerRatingService.addPassengerRating(request);

        assertEquals(DEFAULT_RATING, response.getRating());
        verify(passengerRatingRepository, times(1)).save(passengerRating);
    }

    @Test
    void testAddPassengerRating_Duplicate() {
        when(passengerRatingRepository.existsByPassengerId(DEFAULT_ID)).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> passengerRatingService.addPassengerRating(getDefaultPassengerRatingRequest()));
    }

    @Test
    void testUpdatePassengerRating_Success() {
        PassengerRating existingPassengerRating = getDefaultPassengerRating();
        PassengerRatingRequest updateRequest = getUpdatedPassengerRatingRequest();
        PassengerRating updatedPassengerRating = getUpdatedPassengerRating();

        when(passengerRatingRepository.findById(DEFAULT_ID)).thenReturn(Optional.of(existingPassengerRating));
        doNothing().when(passengerRatingMapper).updatePassengerRatingFromDto(updateRequest, existingPassengerRating);
        when(passengerRatingRepository.save(existingPassengerRating)).thenReturn(updatedPassengerRating);
        when(passengerRatingMapper.toPassengerRatingDto(updatedPassengerRating)).thenReturn(getUpdatedPassengerRatingResponse());

        PassengerRatingResponse response = passengerRatingService.updatePassengerRating(DEFAULT_ID, updateRequest);

        assertEquals(UPDATED_RATING, response.getRating());
        verify(passengerRatingRepository, times(1)).save(existingPassengerRating);
    }

    @Test
    void testDeletePassengerRating_Success() {
        when(passengerRatingRepository.existsById(DEFAULT_ID)).thenReturn(true);

        passengerRatingService.deletePassengerRating(DEFAULT_ID);

        verify(passengerRatingRepository, times(1)).deleteById(DEFAULT_ID);
    }

    @Test
    void testDeletePassengerRating_NotFound() {
        when(passengerRatingRepository.existsById(DEFAULT_ID)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> passengerRatingService.deletePassengerRating(DEFAULT_ID));
    }
}
