package com.software.modsen.rideservice.service.impl;

import com.software.modsen.rideservice.dto.request.DriverRideRequest;
import com.software.modsen.rideservice.dto.request.RideChargeRequest;
import com.software.modsen.rideservice.exception.DuplicateResourceException;
import com.software.modsen.rideservice.exception.ResourceNotFoundException;
import com.software.modsen.rideservice.model.Ride;
import com.software.modsen.rideservice.model.enums.Status;
import com.software.modsen.rideservice.repository.RideRepository;
import com.software.modsen.rideservice.service.DriverService;
import com.software.modsen.rideservice.service.producer.RidePaymentProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.software.modsen.rideservice.utility.Constant.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DriverServiceImpl implements DriverService {

    private final RideRepository rideRepository;
    private final RidePaymentProducer ridePaymentProducer;

    @Override
    public void handleAcceptRide(DriverRideRequest message) {
        if (rideRepository.existsByDriverIdAndStatus(message.getDriverId(), Status.IN_PROGRESS)) {
            throw new DuplicateResourceException(String.format(RIDE_STILL_IN_PROGRESS, message.getDriverId()));
        }

        Ride ride = rideRepository.findById(message.getRideId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format(RIDE_NOT_FOUND_BY_ID, message.getRideId())));
        ride.setDriverId(message.getDriverId());
        ride.setStatus(Status.IN_PROGRESS);
        log.info(RIDE_STATUS_UPDATE, ride.getId(), ride.getStatus());
        rideRepository.save(ride);
    }

    @Override
    public void handleFinishRide(DriverRideRequest message) {
        Ride existingRide = rideRepository.findByDriverIdAndStatus(message.getDriverId(), Status.IN_PROGRESS)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(RIDE_NOT_FOUND_BY_DRIVER_ID_AND_STATUS, message.getRideId())));
        existingRide.setStatus(Status.DESTINATION_REACHED);
        log.info(RIDE_STATUS_UPDATE, existingRide.getId(), existingRide.getStatus());
        Ride unpaidRide = rideRepository.save(existingRide);

        RideChargeRequest rideChargeRequest = RideChargeRequest.builder()
                .passengerId(unpaidRide.getPassengerId())
                .rideId(unpaidRide.getId())
                .currency("usd")
                .amount(Math.round(unpaidRide.getPrice() * 100))
                .action("CHARGE")
                .build();

        log.info(SENDING_MESSAGE, rideChargeRequest.getAction());
        ridePaymentProducer.sendRidePaymentMessage(rideChargeRequest);
    }
}
