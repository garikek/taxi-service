package com.software.modsen.ratingservice.repository;

import com.software.modsen.ratingservice.model.PassengerRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PassengerRatingRepository extends JpaRepository<PassengerRating, Long> {
    boolean existsByPassengerId(Long passengerId);
    Optional<PassengerRating> findByPassengerId(Long passengerId);
    List<PassengerRating> findAllByPassengerId(Long passengerId);
}
