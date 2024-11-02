package com.software.modsen.driverservice.repository;

import com.software.modsen.driverservice.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByEmail(String email);
    Optional<Driver> findByPhoneNumber(String phoneNumber);
    Optional<Driver> findByVehicleNumber(String vehicleNumber);
}
