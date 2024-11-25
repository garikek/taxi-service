package com.software.modsen.ratingservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "passenger_rating")
public class PassengerRating extends BaseRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "passenger_id", nullable = false, unique = true)
    private Long passengerId;

    private Double rating = 0.0;
}
