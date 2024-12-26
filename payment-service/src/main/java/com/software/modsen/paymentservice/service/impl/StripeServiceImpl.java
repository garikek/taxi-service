package com.software.modsen.paymentservice.service.impl;

import com.software.modsen.paymentservice.dto.request.ChargeRequest;
import com.software.modsen.paymentservice.dto.request.CustomerRequest;
import com.software.modsen.paymentservice.exception.CustomerCreateException;
import com.software.modsen.paymentservice.exception.InsufficientBalanceException;
import com.software.modsen.paymentservice.exception.PaymentException;
import com.software.modsen.paymentservice.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.net.RequestOptions;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
import com.stripe.param.PaymentIntentConfirmParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.software.modsen.paymentservice.utility.Constant.*;

@Service
@RequiredArgsConstructor
public class StripeServiceImpl implements StripeService {
    @Value("${stripe.keys.secret}")
    private String secretKey;

    @Override
    public void createPaymentParams(String customerId) {
        Map<String, Object> paymentParams = new HashMap<>();
        paymentParams.put("type", "card");
        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("token", "tok_visa");
        paymentParams.put("card", cardParams);
        createPaymentMethod(paymentParams, customerId);
    }

    @Override
    public Customer createCustomerParams(CustomerRequest customerRequest) {
        CustomerCreateParams customerCreateParams = CustomerCreateParams.builder()
                .setPhone(customerRequest.getPhoneNumber())
                .setEmail(customerRequest.getEmail())
                .setName(customerRequest.getName())
                .setBalance(customerRequest.getAmount())
                .build();

        return createCustomer(customerCreateParams);
    }

    @Override
    public Customer createCustomer(CustomerCreateParams customerCreateParams) {
        RequestOptions requestOptions = RequestOptions.builder()
                .setApiKey(secretKey)
                .build();
        try {
            return Customer.create(customerCreateParams, requestOptions);
        } catch (StripeException ex) {
            throw new CustomerCreateException(ex.getMessage());
        }
    }

    @Override
    public void createPaymentMethod(Map<String, Object> paymentParams, String customerId) {
        Stripe.apiKey = secretKey;
        try {
            PaymentMethod paymentMethod = PaymentMethod.create(paymentParams);
            paymentMethod.attach(Map.of("customer", customerId));
        } catch (StripeException ex) {
            throw new PaymentException(PAYMENT_EXCEPTION + ex.getMessage());
        }
    }

    @Override
    public PaymentIntent confirmIntent(ChargeRequest request, String customerId) {
        Stripe.apiKey = secretKey;
        PaymentIntent intent = createIntent(request, customerId);
        PaymentIntentConfirmParams params =
                PaymentIntentConfirmParams.builder()
                        .setPaymentMethod("pm_card_visa")
                        .build();
        return intentParams(intent, params);
    }

    @Override
    public void changeBalance(String customerId, Long amount) {
        Customer customer = getCustomerByCustomerId(customerId);
        CustomerUpdateParams customerUpdateParams =
                CustomerUpdateParams.builder()
                        .setBalance(customer.getBalance() - amount * 100).build();
        updateCustomer(customerUpdateParams, customer);
    }

    @Override
    public Customer getCustomerByCustomerId(String customerId) {
        try {
            RequestOptions requestOptions = RequestOptions.builder()
                    .setApiKey(secretKey)
                    .build();
            return Customer.retrieve(customerId, requestOptions);
        } catch (StripeException ex) {
            throw new PaymentException(ex.getMessage());
        }
    }

    @Override
    public void updateCustomer(CustomerUpdateParams customerUpdateParams, Customer customer) {
        try {
            RequestOptions requestOptions = RequestOptions.builder()
                    .setApiKey(secretKey)
                    .build();
            customer.update(customerUpdateParams, requestOptions);
        } catch (StripeException ex) {
            throw new InsufficientBalanceException(ex.getMessage());
        }
    }

    @Override
    public PaymentIntent intentParams(PaymentIntent intent, PaymentIntentConfirmParams params) {
        try {
            RequestOptions requestOptions = RequestOptions.builder()
                    .setApiKey(secretKey)
                    .build();
            return intent.confirm(params, requestOptions);
        } catch (StripeException stripeException) {
            throw new PaymentException(stripeException.getMessage());
        }
    }

    private PaymentIntent createIntent(ChargeRequest request, String customerId) {
        try {
            PaymentIntent intent = PaymentIntent.create(Map.of(
                    "amount", request.getAmount(),
                    "currency", request.getCurrency(),
                    "customer", customerId,
                    "automatic_payment_methods", Map.of(
                            "enabled", true,
                            "allow_redirects", "never"
                    )
            ));
            intent.setPaymentMethod(customerId);
            return intent;
        } catch (StripeException stripeException) {
            throw new PaymentException(stripeException.getMessage());
        }
    }

}
