package com.software.modsen.rideservice.service.impl;

import com.software.modsen.rideservice.dto.request.FinishRideRequest;
import com.software.modsen.rideservice.exception.ResourceNotFoundException;
import com.software.modsen.rideservice.model.Ride;
import com.software.modsen.rideservice.model.enums.Status;
import com.software.modsen.rideservice.repository.RideRepository;
import com.software.modsen.rideservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.software.modsen.rideservice.utility.Constant.RIDE_NOT_FOUND_BY_ID;
import static com.software.modsen.rideservice.utility.Constant.RIDE_STATUS_UPDATE;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final RideRepository rideRepository;

    @Override
    public void handleCompletePayment(FinishRideRequest message) {
        Ride ride = rideRepository.findById(message.getRideId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format(RIDE_NOT_FOUND_BY_ID, message.getRideId())));
        ride.setStatus(Status.COMPLETED);
        log.info(RIDE_STATUS_UPDATE, ride.getId(), ride.getStatus());
        rideRepository.save(ride);
    }
}
