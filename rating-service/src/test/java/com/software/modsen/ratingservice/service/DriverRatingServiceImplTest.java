package com.software.modsen.ratingservice.service;

import com.software.modsen.ratingservice.dto.request.DriverRatingRequest;
import com.software.modsen.ratingservice.dto.response.DriverRatingResponse;
import com.software.modsen.ratingservice.exception.DuplicateResourceException;
import com.software.modsen.ratingservice.exception.ResourceNotFoundException;
import com.software.modsen.ratingservice.mapper.DriverRatingMapper;
import com.software.modsen.ratingservice.model.DriverRating;
import com.software.modsen.ratingservice.repository.DriverRatingRepository;
import com.software.modsen.ratingservice.service.impl.DriverRatingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.software.modsen.ratingservice.util.DriverRatingTestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DriverRatingServiceImplTest {
    @Mock
    private DriverRatingRepository driverRatingRepository;
    @Mock
    private DriverRatingMapper driverRatingMapper;
    @InjectMocks
    private DriverRatingServiceImpl driverRatingService;

    @Test
    void testGetDriverRatingById_Success() {
        DriverRating driverRating = getDefaultDriverRating();

        when(driverRatingRepository.findById(DEFAULT_ID)).thenReturn(Optional.of(driverRating));
        when(driverRatingMapper.toDriverRatingDto(driverRating)).thenReturn(getDefaultDriverRatingResponse());

        DriverRatingResponse response = driverRatingService.getDriverRatingById(DEFAULT_ID);

        assertEquals(DEFAULT_RATING, response.getRating());
        verify(driverRatingRepository, times(1)).findById(DEFAULT_ID);
    }

    @Test
    void testGetDriverRatingById_NotFound() {
        when(driverRatingRepository.findById(DEFAULT_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> driverRatingService.getDriverRatingById(DEFAULT_ID));
    }

    @Test
    void testAddDriverRating_Success() {
        DriverRatingRequest request = getDefaultDriverRatingRequest();
        DriverRating driverRating = getDefaultDriverRating();
        DriverRating savedDriverRating = getDefaultDriverRating();

        when(driverRatingMapper.toDriverRatingEntity(request)).thenReturn(driverRating);
        when(driverRatingRepository.save(driverRating)).thenReturn(savedDriverRating);
        when(driverRatingMapper.toDriverRatingDto(savedDriverRating)).thenReturn(getDefaultDriverRatingResponse());

        DriverRatingResponse response = driverRatingService.addDriverRating(request);

        assertEquals(DEFAULT_RATING, response.getRating());
        verify(driverRatingRepository, times(1)).save(driverRating);
    }

    @Test
    void testAddDriverRating_Duplicate() {
        when(driverRatingRepository.existsByDriverId(DEFAULT_ID)).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> driverRatingService.addDriverRating(getDefaultDriverRatingRequest()));
    }

    @Test
    void testUpdateDriverRating_Success() {
        DriverRating existingDriverRating = getDefaultDriverRating();
        DriverRatingRequest updateRequest = getUpdatedDriverRatingRequest();
        DriverRating updatedDriverRating = getUpdatedDriverRating();

        when(driverRatingRepository.findById(DEFAULT_ID)).thenReturn(Optional.of(existingDriverRating));
        doNothing().when(driverRatingMapper).updateDriverRatingFromDto(updateRequest, existingDriverRating);
        when(driverRatingRepository.save(existingDriverRating)).thenReturn(updatedDriverRating);
        when(driverRatingMapper.toDriverRatingDto(updatedDriverRating)).thenReturn(getUpdatedDriverRatingResponse());

        DriverRatingResponse response = driverRatingService.updateDriverRating(DEFAULT_ID, updateRequest);

        assertEquals(UPDATED_RATING, response.getRating());
        verify(driverRatingRepository, times(1)).save(existingDriverRating);
    }

    @Test
    void testDeleteDriverRating_Success() {
        when(driverRatingRepository.existsById(DEFAULT_ID)).thenReturn(true);

        driverRatingService.deleteDriverRating(DEFAULT_ID);

        verify(driverRatingRepository, times(1)).deleteById(DEFAULT_ID);
    }

    @Test
    void testDeleteDriverRating_NotFound() {
        when(driverRatingRepository.existsById(DEFAULT_ID)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> driverRatingService.deleteDriverRating(DEFAULT_ID));
    }
}
