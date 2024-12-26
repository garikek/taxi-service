package com.software.modsen.rideservice.service.impl;

import com.software.modsen.rideservice.dto.request.PassengerRideRequest;
import com.software.modsen.rideservice.dto.request.RideDriverRequest;
import com.software.modsen.rideservice.dto.request.RideRequest;
import com.software.modsen.rideservice.dto.response.RideResponse;
import com.software.modsen.rideservice.exception.ResourceNotFoundException;
import com.software.modsen.rideservice.model.Ride;
import com.software.modsen.rideservice.model.enums.Status;
import com.software.modsen.rideservice.repository.RideRepository;
import com.software.modsen.rideservice.service.PassengerService;
import com.software.modsen.rideservice.service.RideService;
import com.software.modsen.rideservice.service.producer.RideDriverProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

import static com.software.modsen.rideservice.utility.Constant.RIDE_NOT_FOUND_BY_PASSENGER_ID_AND_STATUS;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PassengerServiceImpl implements PassengerService {

    private final RideRepository rideRepository;
    private final RideService rideService;
    private final RideDriverProducer rideDriverProducer;

    @Override
    public RideResponse handleRequestRide(PassengerRideRequest message) {
        double price = (5 + 95 * new Random().nextDouble()) * (1 - message.getPromoCode());
        double roundedPrice = Math.round(price * 100.0) / 100.0;

        RideRequest rideRequest = RideRequest.builder()
                .passengerId(message.getPassengerId())
                .pickupAddress(message.getPickupAddress())
                .destinationAddress(message.getDestinationAddress())
                .price(roundedPrice)
                .status(Status.REQUESTED.toString())
                .build();
        RideResponse requestedRide = rideService.addRide(rideRequest);

        RideDriverRequest rideDriverRequest = RideDriverRequest.builder()
                .rideId(requestedRide.getId())
                .passengerId(requestedRide.getPassengerId())
                .pickupAddress(requestedRide.getPickupAddress())
                .destinationAddress(requestedRide.getDestinationAddress())
                .price(requestedRide.getPrice())
                .action("AVAILABLE")
                .build();
        rideDriverProducer.sendRideDriverMessage(rideDriverRequest);

        return requestedRide;
    }

    @Override
    public void handleCancelRide(PassengerRideRequest message) {
        Ride ride = rideRepository.findByPassengerIdAndStatus(message.getPassengerId(), Status.REQUESTED)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(RIDE_NOT_FOUND_BY_PASSENGER_ID_AND_STATUS, message.getPassengerId())));
        ride.setStatus(Status.CANCELED);
        rideRepository.save(ride);

        RideDriverRequest rideDriverRequest = RideDriverRequest.builder()
                .rideId(ride.getId())
                .passengerId(ride.getPassengerId())
                .action("CANCEL")
                .build();
        rideDriverProducer.sendRideDriverMessage(rideDriverRequest);
    }
}
