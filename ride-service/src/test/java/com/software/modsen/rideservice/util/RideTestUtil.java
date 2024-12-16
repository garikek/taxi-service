package com.software.modsen.rideservice.util;

import com.software.modsen.rideservice.dto.RideRequest;
import com.software.modsen.rideservice.dto.RideResponse;
import com.software.modsen.rideservice.model.Ride;
import com.software.modsen.rideservice.model.enums.Status;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RideTestUtil {
    public static final Long DEFAULT_ID = 1L;
    public static final Long UPDATED_ID = 2L;
    public static final String DEFAULT_PICKUP_ADDRESS = "Vitebsk, frunze, 123-1";
    public static final String UPDATED_PICKUP_ADDRESS = "Minsk, frunze, 123-1";
    public static final String INVALID_PICKUP_ADDRESS = "invalid-pickup-address";
    public static final String DEFAULT_DESTINATION_ADDRESS = "Vitebsk, lazo, 125";
    public static final String UPDATED_DESTINATION_ADDRESS = "Minsk, lazo, 125";
    public static final String INVALID_DESTINATION_ADDRESS = "invalid-destination-address";
    public static final Double DEFAULT_PRICE = 25.0;
    public static final Double UPDATED_PRICE = 30.0;
    public static final Status DEFAULT_STATUS = Status.REQUESTED;
    public static final Status UPDATED_STATUS = Status.COMPLETED;

    public Ride getDefaultRide() {
        return Ride.builder()
                .id(DEFAULT_ID)
                .passengerId(DEFAULT_ID)
                .driverId(DEFAULT_ID)
                .pickupAddress(DEFAULT_PICKUP_ADDRESS)
                .destinationAddress(DEFAULT_DESTINATION_ADDRESS)
                .price(DEFAULT_PRICE)
                .status(DEFAULT_STATUS)
                .build();
    }

    public Ride getUpdatedRide() {
        return Ride.builder()
                .id(UPDATED_ID)
                .passengerId(UPDATED_ID)
                .driverId(UPDATED_ID)
                .pickupAddress(UPDATED_PICKUP_ADDRESS)
                .destinationAddress(UPDATED_DESTINATION_ADDRESS)
                .price(UPDATED_PRICE)
                .status(UPDATED_STATUS)
                .build();
    }

    public Ride getDefaultRideWithInvalidPickupAddress() {
        return Ride.builder()
                .passengerId(DEFAULT_ID)
                .driverId(DEFAULT_ID)
                .pickupAddress(INVALID_PICKUP_ADDRESS)
                .destinationAddress(DEFAULT_DESTINATION_ADDRESS)
                .price(DEFAULT_PRICE)
                .status(DEFAULT_STATUS)
                .build();
    }

    public Ride getDefaultRideWithInvalidDestinationAddress() {
        return Ride.builder()
                .passengerId(DEFAULT_ID)
                .driverId(DEFAULT_ID)
                .pickupAddress(DEFAULT_PICKUP_ADDRESS)
                .destinationAddress(INVALID_DESTINATION_ADDRESS)
                .price(DEFAULT_PRICE)
                .status(DEFAULT_STATUS)
                .build();
    }

    public RideRequest getDefaultRideRequest() {
        return RideRequest.builder()
                .passengerId(DEFAULT_ID)
                .driverId(DEFAULT_ID)
                .pickupAddress(DEFAULT_PICKUP_ADDRESS)
                .destinationAddress(DEFAULT_DESTINATION_ADDRESS)
                .price(DEFAULT_PRICE)
                .status(DEFAULT_STATUS.name())
                .build();
    }

    public RideRequest getUpdatedRideRequest() {
        return RideRequest.builder()
                .passengerId(UPDATED_ID)
                .driverId(UPDATED_ID)
                .pickupAddress(UPDATED_PICKUP_ADDRESS)
                .destinationAddress(UPDATED_DESTINATION_ADDRESS)
                .price(UPDATED_PRICE)
                .status(UPDATED_STATUS.name())
                .build();
    }

    public RideRequest getDefaultRideRequestWithInvalidPickupAddress() {
        return RideRequest.builder()
                .passengerId(DEFAULT_ID)
                .driverId(DEFAULT_ID)
                .pickupAddress(INVALID_PICKUP_ADDRESS)
                .destinationAddress(DEFAULT_DESTINATION_ADDRESS)
                .price(DEFAULT_PRICE)
                .status(DEFAULT_STATUS.name())
                .build();
    }

    public RideRequest getDefaultRideRequestWithInvalidDestinationAddress() {
        return RideRequest.builder()
                .passengerId(DEFAULT_ID)
                .driverId(DEFAULT_ID)
                .pickupAddress(DEFAULT_PICKUP_ADDRESS)
                .destinationAddress(INVALID_DESTINATION_ADDRESS)
                .price(DEFAULT_PRICE)
                .status(DEFAULT_STATUS.name())
                .build();
    }

    public RideResponse getDefaultRideResponse() {
        return RideResponse.builder()
                .id(DEFAULT_ID)
                .passengerId(DEFAULT_ID)
                .driverId(DEFAULT_ID)
                .pickupAddress(DEFAULT_PICKUP_ADDRESS)
                .destinationAddress(DEFAULT_DESTINATION_ADDRESS)
                .price(DEFAULT_PRICE)
                .status(DEFAULT_STATUS.name())
                .build();
    }

    public RideResponse getUpdatedRideResponse() {
        return RideResponse.builder()
                .id(UPDATED_ID)
                .passengerId(UPDATED_ID)
                .driverId(UPDATED_ID)
                .pickupAddress(UPDATED_PICKUP_ADDRESS)
                .destinationAddress(UPDATED_DESTINATION_ADDRESS)
                .price(UPDATED_PRICE)
                .status(UPDATED_STATUS.name())
                .build();
    }
}
