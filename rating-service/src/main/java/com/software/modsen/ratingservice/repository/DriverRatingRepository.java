package com.software.modsen.ratingservice.repository;

import com.software.modsen.ratingservice.model.DriverRating;
import com.software.modsen.ratingservice.model.PassengerRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverRatingRepository extends JpaRepository<DriverRating, Long> {
    boolean existsByDriverId(Long driverId);
    Optional<DriverRating> findByDriverId(Long driverId);
}
