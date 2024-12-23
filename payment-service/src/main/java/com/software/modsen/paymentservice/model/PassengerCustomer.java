package com.software.modsen.paymentservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PassengerCustomerId.class)
@Table(name = "passengers_customers")
@EntityListeners(AuditingEntityListener.class)
public class PassengerCustomer {
    @Id
    @Column(name = "passenger_id", nullable = false)
    private Long passengerId;

    @Id
    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
