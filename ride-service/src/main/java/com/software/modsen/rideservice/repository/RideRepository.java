package com.software.modsen.rideservice.repository;

import com.software.modsen.rideservice.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RideRepository extends JpaRepository<Ride, Long> {
}
