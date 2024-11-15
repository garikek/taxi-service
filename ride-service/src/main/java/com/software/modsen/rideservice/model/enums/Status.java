package com.software.modsen.rideservice.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    REQUESTED("Requested by passenger"),
    ACCEPTED("Accepted by driver"),
    IN_PROGRESS("Ride is in progress"),
    COMPLETED("Ride has been completed"),
    CANCELED("Ride was cancelled");

    private final String description;

    @Override
    public String toString() {
        return name().toUpperCase();
    }
}
