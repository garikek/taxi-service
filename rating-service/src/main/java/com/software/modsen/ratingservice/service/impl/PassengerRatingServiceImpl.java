package com.software.modsen.ratingservice.service.impl;

import com.software.modsen.ratingservice.dto.request.PassengerRatingRequest;
import com.software.modsen.ratingservice.dto.response.PassengerRatingResponse;
import com.software.modsen.ratingservice.dto.response.PassengerRatingResponseList;
import com.software.modsen.ratingservice.exception.DuplicateResourceException;
import com.software.modsen.ratingservice.exception.ResourceNotFoundException;
import com.software.modsen.ratingservice.mapper.PassengerRatingMapper;
import com.software.modsen.ratingservice.model.PassengerRating;
import com.software.modsen.ratingservice.repository.PassengerRatingRepository;
import com.software.modsen.ratingservice.service.PassengerRatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static com.software.modsen.ratingservice.utility.Constant.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PassengerRatingServiceImpl implements PassengerRatingService {
    private final PassengerRatingRepository passengerRatingRepository;
    private final PassengerRatingMapper passengerRatingMapper;

    @Override
    public PassengerRatingResponseList getPassengerRatings() {
        return new PassengerRatingResponseList(passengerRatingRepository.findAll().stream()
                .map(passengerRatingMapper::toPassengerRatingDto)
                .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public PassengerRatingResponse addPassengerRating(PassengerRatingRequest passengerRatingRequest) {
        checkPassengerRatingExist(passengerRatingRequest.getPassengerId());
        PassengerRating passengerRating = passengerRatingMapper.toPassengerRatingEntity(passengerRatingRequest);
        PassengerRating savedPassengerRating = passengerRatingRepository.save(passengerRating);
        return passengerRatingMapper.toPassengerRatingDto(savedPassengerRating);
    }

    @Override
    public PassengerRatingResponse getPassengerRatingById(Long id) {
        return passengerRatingMapper.toPassengerRatingDto(getByIdOrThrow(id));
    }

    @Override
    @Transactional
    public void deletePassengerRating(Long id) {
        if(!passengerRatingRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format(PASSENGER_RATING_NOT_FOUND_BY_ID, id));
        }
        passengerRatingRepository.deleteById(id);
    }

    @Override
    @Transactional
    public PassengerRatingResponse updatePassengerRating(Long id, PassengerRatingRequest passengerRatingRequest) {
        PassengerRating existingPassengerRating = getByIdOrThrow(id);
        passengerRatingMapper.updatePassengerRatingFromDto(passengerRatingRequest, existingPassengerRating);
        PassengerRating updatedPassengerRating = passengerRatingRepository.save(existingPassengerRating);
        return passengerRatingMapper.toPassengerRatingDto(updatedPassengerRating);
    }

    @Override
    @Transactional
    public void deletePassengerRatingByPassengerId(Long passengerId) {
        PassengerRating rating = passengerRatingRepository.findByPassengerId(passengerId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(PASSENGER_RATING_NOT_FOUND_BY_PASSENGER_ID, passengerId)));
        passengerRatingRepository.delete(rating);
    }

    @Override
    @Transactional
    public PassengerRatingResponse updatePassengerRatingByPassengerId(Long passengerId, PassengerRatingRequest passengerRatingRequest) {
        PassengerRating rating = passengerRatingRepository.findByPassengerId(passengerId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(PASSENGER_RATING_NOT_FOUND_BY_PASSENGER_ID, passengerId)));
        passengerRatingMapper.updatePassengerRatingFromDto(passengerRatingRequest, rating);
        PassengerRating updatedRating = passengerRatingRepository.save(rating);
        return passengerRatingMapper.toPassengerRatingDto(updatedRating);
    }

    private PassengerRating getByIdOrThrow(Long id){
        return passengerRatingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(PASSENGER_RATING_NOT_FOUND_BY_ID, id)));
    }

    private void checkPassengerRatingExist(Long passengerId){
        if (passengerRatingRepository.existsByPassengerId(passengerId)) {
            throw new DuplicateResourceException(String.format(DUPLICATE_PASSENGER_RATING, passengerId));
        }
    }
}
