package com.software.modsen.rideservice.service.impl;

import com.software.modsen.rideservice.dto.RideListDto;
import com.software.modsen.rideservice.dto.RideRequest;
import com.software.modsen.rideservice.dto.RideResponse;
import com.software.modsen.rideservice.exception.InvalidResourceException;
import com.software.modsen.rideservice.exception.ResourceNotFoundException;
import com.software.modsen.rideservice.mapper.RideMapper;
import com.software.modsen.rideservice.model.Ride;
import com.software.modsen.rideservice.repository.RideRepository;
import com.software.modsen.rideservice.service.RideService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.software.modsen.rideservice.utility.Constant.INVALID_ADDRESS;
import static com.software.modsen.rideservice.utility.Constant.RIDE_NOT_FOUND_BY_ID;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultRideService implements RideService {
    private final RideRepository rideRepository;
    private final RideMapper rideMapper;

    private static final Pattern ADDRESS_PATTERN = Pattern.compile("^[А-Яа-яЁёA-Za-z\\s]+,\\s[А-Яа-яЁёA-Za-z\\s]+,\\s\\d+(-\\d+)?$");

    @Override
    public RideListDto getRides() {
        return new RideListDto(rideRepository.findAll().stream()
                .map(rideMapper::toRideDto)
                .collect(Collectors.toList()));
    }

    @Override
    public RideResponse addRide(RideRequest rideRequest) {
        Ride ride = rideMapper.toRideEntity(rideRequest);
        validateAddRide(ride);
        Ride savedRide = rideRepository.save(ride);
        return rideMapper.toRideDto(savedRide);
    }

    @Override
    public RideResponse getRideById(Long id) {
        return rideMapper.toRideDto(getByIdOrThrow(id));
    }

    @Override
    public void deleteRide(Long id) {
        if(!rideRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format(RIDE_NOT_FOUND_BY_ID, id));
        }
        rideRepository.deleteById(id);
    }

    @Override
    public RideResponse updateRide(Long id, RideRequest rideRequest) {
        Ride existingRide = getByIdOrThrow(id);
        rideMapper.updateRideFromDto(rideRequest, existingRide);
        validateUpdateRide(existingRide);
        Ride updatedRide = rideRepository.save(existingRide);
        return rideMapper.toRideDto(updatedRide);
    }

    private void validateAddRide(Ride ride){
        validateAddress(ride.getPickupAddress());
        validateAddress(ride.getDestinationAddress());
    }

    private void validateUpdateRide(Ride ride){
        validateAddress(ride.getPickupAddress());
        validateAddress(ride.getDestinationAddress());
    }

    private void validateAddress (String address){
        if (!ADDRESS_PATTERN.matcher(address).matches()) {
            throw new InvalidResourceException(String.format(INVALID_ADDRESS, address));
        }
    }

    private Ride getByIdOrThrow(Long id){
        return rideRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(RIDE_NOT_FOUND_BY_ID, id)));
    }
}
