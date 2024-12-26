package com.software.modsen.rideservice.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    REQUESTED("RIde requested by passenger"),
    IN_PROGRESS("Ride accepted by driver and is in progress"),
    DESTINATION_REACHED("Destination reached, awaiting payment"),
    COMPLETED("Ride has been completed"),
    CANCELED("Ride was cancelled");

    private final String description;

    @Override
    public String toString() {
        return name().toUpperCase();
    }
}
