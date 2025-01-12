package com.software.modsen.rideservice.service.impl;

import com.software.modsen.rideservice.dto.response.RideListDto;
import com.software.modsen.rideservice.dto.request.RideRequest;
import com.software.modsen.rideservice.dto.response.RideResponse;
import com.software.modsen.rideservice.exception.InvalidResourceException;
import com.software.modsen.rideservice.exception.ResourceNotFoundException;
import com.software.modsen.rideservice.mapper.RideMapper;
import com.software.modsen.rideservice.model.Ride;
import com.software.modsen.rideservice.model.enums.Status;
import com.software.modsen.rideservice.repository.RideRepository;
import com.software.modsen.rideservice.service.RideService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.software.modsen.rideservice.utility.Constant.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultRideService implements RideService {
    private final RideRepository rideRepository;
    private final RideMapper rideMapper;

    private static final Pattern ADDRESS_PATTERN = Pattern.compile("^[А-Яа-яЁёA-Za-z\\s]+,\\s[А-Яа-яЁёA-Za-z\\s]+,\\s\\d+(-\\d+)?$");

    @Override
    public RideListDto getRides() {
        log.info(GET_RIDES);
        return new RideListDto(rideRepository.findAll().stream()
                .map(rideMapper::toRideDto)
                .collect(Collectors.toList()));
    }

    @Override
    public RideResponse addRide(RideRequest rideRequest) {
        Ride ride = rideMapper.toRideEntity(rideRequest);
        validateAddRide(ride);
        log.info(ADD_RIDE);
        Ride savedRide = rideRepository.save(ride);
        return rideMapper.toRideDto(savedRide);
    }

    @Override
    public RideResponse getRideById(Long id) {
        log.info(GET_RIDE_BY_ID, id);
        return rideMapper.toRideDto(getByIdOrThrow(id));
    }

    @Override
    public void deleteRide(Long id) {
        if(!rideRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format(RIDE_NOT_FOUND_BY_ID, id));
        }
        log.info(DELETE_RIDE);
        rideRepository.deleteById(id);
    }

    @Override
    public RideResponse updateRide(Long id, RideRequest rideRequest) {
        Ride existingRide = getByIdOrThrow(id);
        rideMapper.updateRideFromDto(rideRequest, existingRide);
        validateUpdateRide(existingRide);
        log.info(UPDATE_RIDE);
        Ride updatedRide = rideRepository.save(existingRide);
        return rideMapper.toRideDto(updatedRide);
    }

    @Override
    public RideListDto getCompletedRidesByDriverId(Long driverId) {
        log.info(GET_COMPLETED_RIDES_BY_DRIVER_ID, driverId);
        return new RideListDto(rideRepository.findAllByDriverIdAndStatus(driverId, Status.COMPLETED).stream()
                .map(rideMapper::toRideDto)
                .collect(Collectors.toList()));
    }

    @Override
    public RideListDto getCompletedRidesByPassengerId(Long passengerId) {
        log.info(GET_COMPLETED_RIDES_BY_PASSENGER_ID, passengerId);
        return new RideListDto(rideRepository.findAllByPassengerIdAndStatus(passengerId, Status.COMPLETED).stream()
                .map(rideMapper::toRideDto)
                .collect(Collectors.toList()));
    }

    @Override
    public Double getEstimatedRidePrice(Long id) {
        log.info(GET_ESTIMATED_RIDE_PRICE, id);
        Ride ride = getByIdOrThrow(id);
        return ride.getPrice();
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
