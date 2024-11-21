package com.software.modsen.ratingservice.service.impl;

import com.software.modsen.ratingservice.dto.request.DriverRatingRequest;
import com.software.modsen.ratingservice.dto.response.DriverRatingResponse;
import com.software.modsen.ratingservice.dto.response.DriverRatingResponseList;
import com.software.modsen.ratingservice.exception.DuplicateResourceException;
import com.software.modsen.ratingservice.exception.ResourceNotFoundException;
import com.software.modsen.ratingservice.mapper.DriverRatingMapper;
import com.software.modsen.ratingservice.model.DriverRating;
import com.software.modsen.ratingservice.repository.DriverRatingRepository;
import com.software.modsen.ratingservice.service.DriverRatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static com.software.modsen.ratingservice.utility.Constant.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class DriverRatingServiceImpl implements DriverRatingService {
    private final DriverRatingRepository driverRatingRepository;
    private final DriverRatingMapper driverRatingMapper;

    @Override
    public DriverRatingResponseList getDriverRatings() {
        return new DriverRatingResponseList(driverRatingRepository.findAll().stream()
                .map(driverRatingMapper::toDriverRatingDto)
                .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public DriverRatingResponse addDriverRating(DriverRatingRequest driverRatingRequest) {
        DriverRating driverRating = driverRatingMapper.toDriverRatingEntity(driverRatingRequest);
        DriverRating savedDriverRating = driverRatingRepository.save(driverRating);
        return driverRatingMapper.toDriverRatingDto(savedDriverRating);
    }

    @Override
    public DriverRatingResponse getDriverRatingById(Long id) {
        return driverRatingMapper.toDriverRatingDto(getByIdOrThrow(id));
    }

    @Override
    @Transactional
    public void deleteDriverRating(Long id) {
        if(!driverRatingRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format(DRIVER_RATING_NOT_FOUND_BY_ID, id));
        }
        driverRatingRepository.deleteById(id);
    }

    @Override
    @Transactional
    public DriverRatingResponse updateDriverRating(Long id, DriverRatingRequest driverRatingRequest) {
        DriverRating existingDriverRating = getByIdOrThrow(id);
        driverRatingMapper.updateDriverRatingFromDto(driverRatingRequest, existingDriverRating);
        DriverRating updatedDriverRating = driverRatingRepository.save(existingDriverRating);
        return driverRatingMapper.toDriverRatingDto(updatedDriverRating);
    }

    @Override
    @Transactional
    public void deleteDriverRatingByDriverId(Long driverId) {
        DriverRating rating = driverRatingRepository.findByDriverId(driverId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(DRIVER_RATING_NOT_FOUND_BY_DRIVER_ID, driverId)));
        driverRatingRepository.delete(rating);
    }

    @Override
    @Transactional
    public DriverRatingResponse updateDriverRatingByDriverId(Long driverId, DriverRatingRequest driverRatingRequest) {
        DriverRating rating = driverRatingRepository.findByDriverId(driverId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(DRIVER_RATING_NOT_FOUND_BY_DRIVER_ID, driverId)));
        driverRatingMapper.updateDriverRatingFromDto(driverRatingRequest, rating);
        DriverRating updatedRating = driverRatingRepository.save(rating);
        return driverRatingMapper.toDriverRatingDto(updatedRating);
    }

    private DriverRating getByIdOrThrow(Long id){
        return driverRatingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(DRIVER_RATING_NOT_FOUND_BY_ID, id)));
    }

    private void checkDriverRatingExist(Long driverId){
        if (driverRatingRepository.existsByDriverId(driverId)) {
            throw new DuplicateResourceException(String.format(DUPLICATE_DRIVER_RATING, driverId));
        }
    }
}
