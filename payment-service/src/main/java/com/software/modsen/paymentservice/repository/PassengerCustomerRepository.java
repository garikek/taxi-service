package com.software.modsen.paymentservice.repository;

import com.software.modsen.paymentservice.model.PassengerCustomer;
import com.software.modsen.paymentservice.model.PassengerCustomerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassengerCustomerRepository extends JpaRepository<PassengerCustomer, PassengerCustomerId> {
    Optional<PassengerCustomer> findByPassengerId(Long id);
    boolean existsByPassengerId(Long passengerId);
}
