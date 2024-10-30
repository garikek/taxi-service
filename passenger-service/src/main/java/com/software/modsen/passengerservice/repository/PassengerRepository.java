package com.software.modsen.passengerservice.repository;

import com.software.modsen.passengerservice.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    Optional<Passenger> findByEmail(String email);
    Optional<Passenger> findByPhoneNumber(String phoneNumber);
}