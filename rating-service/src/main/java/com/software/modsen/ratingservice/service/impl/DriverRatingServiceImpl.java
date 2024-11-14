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

import java.util.stream.Collectors;

import static com.software.modsen.ratingservice.utility.Constant.DUPLICATE_DRIVER_RATING;
import static com.software.modsen.ratingservice.utility.Constant.DRIVER_RATING_NOT_FOUND_BY_ID;

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
    public DriverRatingResponse addDriverRating(DriverRatingRequest driverRatingRequest) {
        checkDriverRatingExist(driverRatingRequest.getDriverId());
        DriverRating driverRating = driverRatingMapper.toDriverRatingEntity(driverRatingRequest);
        DriverRating savedDriverRating = driverRatingRepository.save(driverRating);
        return driverRatingMapper.toDriverRatingDto(savedDriverRating);
    }

    @Override
    public DriverRatingResponse getDriverRatingById(Long id) {
        return driverRatingMapper.toDriverRatingDto(getByIdOrThrow(id));
    }

    @Override
    public void deleteDriverRating(Long id) {
        if(!driverRatingRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format(DRIVER_RATING_NOT_FOUND_BY_ID, id));
        }
        driverRatingRepository.deleteById(id);
    }

    @Override
    public DriverRatingResponse updateDriverRating(Long id, DriverRatingRequest driverRatingRequest) {
        DriverRating existingDriverRating = getByIdOrThrow(id);
        driverRatingMapper.updateDriverRatingFromDto(driverRatingRequest, existingDriverRating);
        DriverRating updatedDriverRating = driverRatingRepository.save(existingDriverRating);
        return driverRatingMapper.toDriverRatingDto(updatedDriverRating);
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
