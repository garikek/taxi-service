package com.software.modsen.passengerservice.service.impl;

import com.software.modsen.passengerservice.client.RideFeignClient;
import com.software.modsen.passengerservice.dto.request.RideRequest;
import com.software.modsen.passengerservice.dto.response.RideListDto;
import com.software.modsen.passengerservice.service.RideService;
import com.software.modsen.passengerservice.service.producer.PassengerRideProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.software.modsen.passengerservice.utility.Constant.SENDING_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {
    private final PassengerRideProducer passengerRideProducer;
    private final RideFeignClient rideFeignClient;

    @Override
    public void requestRide(RideRequest rideRequest) {
        RideRequest message = RideRequest.builder()
                .passengerId(rideRequest.getPassengerId())
                .pickupAddress(rideRequest.getPickupAddress())
                .destinationAddress(rideRequest.getDestinationAddress())
                .promoCode(rideRequest.getPromoCode())
                .action("REQUEST")
                .build();
        log.info(SENDING_MESSAGE, message.getAction());
        passengerRideProducer.sendPassengerRideMessage(message);
    }

    @Override
    public void cancelRide(Long passengerId) {
        RideRequest message = RideRequest.builder()
                .passengerId(passengerId)
                .action("CANCEL")
                .build();
        log.info(SENDING_MESSAGE, message.getAction());
        passengerRideProducer.sendPassengerRideMessage(message);
    }

    @Override
    public Double getEstimatedRidePrice(Long rideId) {
        return rideFeignClient.getEstimatedRidePrice(rideId);
    }

    @Override
    public RideListDto getRideHistory(Long passengerId) {
        return rideFeignClient.getRideHistory(passengerId);
    }
}
