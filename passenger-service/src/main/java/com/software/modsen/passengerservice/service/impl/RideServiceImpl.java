package com.software.modsen.passengerservice.service.impl;

import com.software.modsen.passengerservice.dto.request.RideRequest;
import com.software.modsen.passengerservice.service.RideService;
import com.software.modsen.passengerservice.service.producer.PassengerRideProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {
    private final PassengerRideProducer passengerRideProducer;

    @Override
    public void requestRide(RideRequest rideRequest) {
        RideRequest message = RideRequest.builder()
                .passengerId(rideRequest.getPassengerId())
                .pickupAddress(rideRequest.getPickupAddress())
                .destinationAddress(rideRequest.getDestinationAddress())
                .promoCode(rideRequest.getPromoCode())
                .action("REQUEST")
                .build();
        passengerRideProducer.sendPassengerRideMessage(message);
    }

    @Override
    public void cancelRide(Long passengerId) {
        RideRequest message = RideRequest.builder()
                .passengerId(passengerId)
                .action("CANCEL")
                .build();
        passengerRideProducer.sendPassengerRideMessage(message);
    }
}
