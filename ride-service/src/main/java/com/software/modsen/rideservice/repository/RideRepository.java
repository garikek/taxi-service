package com.software.modsen.rideservice.repository;

import com.software.modsen.rideservice.model.Ride;
import com.software.modsen.rideservice.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RideRepository extends JpaRepository<Ride, Long> {
    boolean existsByPickupAddressAndDestinationAddress(String pickupAddress, String destinationAddress);
    Optional<Ride> findByPassengerId(Long passengerId);
    Optional<Ride> findByPassengerIdAndStatus(Long passengerId, Status status);
    Optional<Ride> findByDriverIdAndStatus(Long driverId, Status status);
    boolean existsByDriverId(Long driverId);
    boolean existsByDriverIdAndStatus(Long driverId, Status status);
    List<Ride> findAllByDriverIdAndStatus(Long driverId, Status status);
    List<Ride> findAllByPassengerIdAndStatus(Long passengerId, Status status);
}
