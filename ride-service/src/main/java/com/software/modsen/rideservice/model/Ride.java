package com.software.modsen.rideservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rides")
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "passenger_id", nullable = false)
    private Long passengerId;

    @Column(name = "driver_id", nullable = false)
    private Long driverId;

    @Column(name = "pickup_address", nullable = false)
    private String pickupAddress;

    @Column(name = "destination_address", nullable = false)
    private String destinationAddress;

    @Column(name = "estimated_cost")
    private Double estimatedCost = 0.0;

    @Column(name = "actual_cost")
    private Double actualCost = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

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
}
