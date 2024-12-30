package com.software.modsen.paymentservice.service.impl;

import com.software.modsen.paymentservice.dto.request.ChargeRequest;
import com.software.modsen.paymentservice.dto.request.CustomerRequest;
import com.software.modsen.paymentservice.dto.response.ChargeResponse;
import com.software.modsen.paymentservice.dto.response.CustomerResponse;
import com.software.modsen.paymentservice.exception.DuplicateResourceException;
import com.software.modsen.paymentservice.exception.ResourceNotFoundException;
import com.software.modsen.paymentservice.model.PassengerCustomer;
import com.software.modsen.paymentservice.repository.PassengerCustomerRepository;
import com.software.modsen.paymentservice.service.PaymentService;
import com.software.modsen.paymentservice.service.StripeService;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.software.modsen.paymentservice.utility.Constant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PassengerCustomerRepository passengerCustomerRepository;
    private final StripeService stripeService;

    @Transactional
    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        checkDuplicateCustomer(customerRequest.getPassengerId());

        log.info(CREATE_CUSTOMER, customerRequest.getPassengerId());
        Customer customer = stripeService.createCustomerParams(customerRequest);
        stripeService.createPaymentParams(customer.getId());

        PassengerCustomer passengerCustomer = PassengerCustomer.builder()
                        .customerId(customer.getId())
                        .passengerId(customerRequest.getPassengerId())
                        .build();
        passengerCustomerRepository.save(passengerCustomer);

        return CustomerResponse.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .name(customer.getName())
                .build();
    }

    @Override
    public CustomerResponse getCustomerByPassengerId(Long passengerId) {
        PassengerCustomer user = getCustomerByPassengerIdOrThrow(passengerId);
        String customerId = user.getCustomerId();
        log.info(GET_CUSTOMER_BY_PASSENGER_ID, passengerId);
        Customer customer = stripeService.getCustomerByCustomerId(customerId);
        return CustomerResponse.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .name(customer.getName())
                .phone(customer.getPhone())
                .build();
    }

    @Transactional
    @Override
    public ChargeResponse chargeCustomer(ChargeRequest chargeRequest) {
        PassengerCustomer user = getCustomerByPassengerIdOrThrow(chargeRequest.getPassengerId());

        String customerId = user.getCustomerId();
        log.info(CHARGE_PASSENGER, chargeRequest.getPassengerId(), chargeRequest.getAmount(), chargeRequest.getCurrency());
        PaymentIntent paymentIntent = stripeService.confirmIntent(chargeRequest, customerId);
        stripeService.changeBalance(customerId, chargeRequest.getAmount());

        return ChargeResponse.builder()
                .id(paymentIntent.getId())
                .amount(paymentIntent.getAmount() / 100)
                .currency(paymentIntent.getCurrency()).build();
    }

    private void checkDuplicateCustomer(Long passengerId) {
        if (passengerCustomerRepository.existsByPassengerId(passengerId)) {
            throw new DuplicateResourceException(String.format(DUPLICATE_CUSTOMER, passengerId));
        }
    }

    private PassengerCustomer getCustomerByPassengerIdOrThrow(Long passengerId) {
        return passengerCustomerRepository.findByPassengerId(passengerId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(CUSTOMER_NOT_FOUND_BY_ID, passengerId)));
    }

}
