package com.software.modsen.paymentservice.service;

import com.software.modsen.paymentservice.dto.request.ChargeRequest;
import com.software.modsen.paymentservice.dto.request.CustomerRequest;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
import com.stripe.param.PaymentIntentConfirmParams;

import java.util.Map;

public interface StripeService {
    void createPaymentParams(String customerId);
    Customer createCustomerParams(CustomerRequest customerRequest);
    Customer createCustomer(CustomerCreateParams customerCreateParams);
    void createPaymentMethod(Map<String, Object> paymentParams, String customerId);
    PaymentIntent confirmIntent(ChargeRequest request, String customerId);
    void changeBalance(String customerId, Long amount);
    Customer getCustomerByCustomerId(String customerId);
    void updateCustomer(CustomerUpdateParams customerUpdateParams, Customer customer);
    PaymentIntent intentParams(PaymentIntent intent, PaymentIntentConfirmParams params);
}
