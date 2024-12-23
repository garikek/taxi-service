package com.software.modsen.paymentservice.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerCustomerId implements Serializable {
    private Long passengerId;
    private String customerId;
}
