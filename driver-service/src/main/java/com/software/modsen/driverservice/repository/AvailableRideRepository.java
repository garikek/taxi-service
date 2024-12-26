package com.software.modsen.driverservice.repository;

import com.software.modsen.driverservice.model.AvailableRide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvailableRideRepository extends JpaRepository<AvailableRide, Long> {
    Optional<AvailableRide> findByRideIdAndPassengerId(Long rideId, Long passengerId);
    Optional<AvailableRide> findByRideId(Long rideId);
}
