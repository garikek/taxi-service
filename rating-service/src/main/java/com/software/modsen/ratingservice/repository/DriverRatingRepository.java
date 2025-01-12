package com.software.modsen.ratingservice.repository;

import com.software.modsen.ratingservice.model.DriverRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRatingRepository extends JpaRepository<DriverRating, Long> {
    boolean existsByDriverId(Long driverId);
    Optional<DriverRating> findByDriverId(Long driverId);
    List<DriverRating> findAllByDriverId(Long driverId);
}
