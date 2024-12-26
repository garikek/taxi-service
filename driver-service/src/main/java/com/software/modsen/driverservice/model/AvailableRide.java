package com.software.modsen.driverservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "available_rides")
public class AvailableRide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ride_id", nullable = false)
    private Long rideId;

    @Column(name = "passenger_id", nullable = false)
    private Long passengerId;

    @Column(name = "pickup_address", nullable = false)
    private String pickupAddress;

    @Column(name = "destination_address", nullable = false)
    private String destinationAddress;

    @Column(nullable = false)
    private Double price;
}
