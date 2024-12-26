package com.software.modsen.driverservice.service.impl;

import com.software.modsen.driverservice.dto.request.AvailableRideRequest;
import com.software.modsen.driverservice.dto.request.DriverRideRequest;
import com.software.modsen.driverservice.dto.request.RideDriverRequest;
import com.software.modsen.driverservice.dto.response.AvailableRideResponse;
import com.software.modsen.driverservice.exception.ResourceNotFoundException;
import com.software.modsen.driverservice.mapper.AvailableRideMapper;
import com.software.modsen.driverservice.model.AvailableRide;
import com.software.modsen.driverservice.repository.AvailableRideRepository;
import com.software.modsen.driverservice.repository.DriverRepository;
import com.software.modsen.driverservice.service.AvailableRideService;
import com.software.modsen.driverservice.service.producer.DriverRideProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.software.modsen.driverservice.utility.Constant.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class AvailableRideServiceImpl implements AvailableRideService {
    private final AvailableRideRepository rideRepository;
    private final DriverRepository driverRepository;
    private final AvailableRideMapper rideMapper;
    private final DriverRideProducer rideProducer;

    @Transactional
    @Override
    public AvailableRideResponse addRide(RideDriverRequest rideDriverRequest) {
        AvailableRideRequest rideRequest = AvailableRideRequest.builder()
                .rideId(rideDriverRequest.getRideId())
                .passengerId(rideDriverRequest.getPassengerId())
                .pickupAddress(rideDriverRequest.getPickupAddress())
                .destinationAddress(rideDriverRequest.getDestinationAddress())
                .price(rideDriverRequest.getPrice())
                .build();

        AvailableRide ride = rideMapper.toEntity(rideRequest);
        AvailableRide savedRide = rideRepository.save(ride);
        return rideMapper.toDto(savedRide);
    }

    @Transactional
    @Override
    public void deleteRide(Long id) {
        if (!rideRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format(RIDE_NOT_FOUND_BY_ID, id));
        }
        rideRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteRide(Long rideId, Long passengerId) {
        AvailableRide ride = rideRepository.findByRideIdAndPassengerId(rideId, passengerId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(RIDE_NOT_FOUND_BY_RIDE_ID_AND_PASSENGER_ID, rideId, passengerId)));
        rideRepository.deleteById(ride.getId());
    }

    @Transactional
    @Override
    public void acceptRide(DriverRideRequest rideRequest) {
        AvailableRide ride = rideRepository.findByRideId(rideRequest.getRideId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format(RIDE_NOT_FOUND_BY_RIDE_ID, rideRequest.getRideId())));

        driverRepository.findById(rideRequest.getDriverId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format(DRIVER_NOT_FOUND_BY_ID, rideRequest.getDriverId())));

        rideRequest.setAction("ACCEPT");

        rideProducer.sendDriverRideMessage(rideRequest);
        deleteRide(ride.getId());
    }

    @Override
    public void finishRide(Long driverId) {
        DriverRideRequest message = DriverRideRequest.builder()
                .driverId(driverId)
                .action("FINISH")
                .build();
        rideProducer.sendDriverRideMessage(message);
    }
}
