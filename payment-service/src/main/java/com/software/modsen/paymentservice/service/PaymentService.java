package com.software.modsen.paymentservice.service;

import com.software.modsen.paymentservice.dto.request.ChargeRequest;
import com.software.modsen.paymentservice.dto.request.CustomerRequest;
import com.software.modsen.paymentservice.dto.response.ChargeResponse;
import com.software.modsen.paymentservice.dto.response.CustomerResponse;

public interface PaymentService {
    CustomerResponse createCustomer(CustomerRequest customerRequest);
    CustomerResponse getCustomerByPassengerId(Long passengerId);
    ChargeResponse chargeCustomer(ChargeRequest chargeRequest);
}
