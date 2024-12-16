package com.software.modsen.passengerservice.util;

import com.software.modsen.passengerservice.dto.request.PassengerRequest;
import com.software.modsen.passengerservice.dto.response.PassengerResponse;
import com.software.modsen.passengerservice.model.Passenger;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PassengerTestUtil {
    public static final Long DEFAULT_PASSENGER_ID = 1L;
    public static final Long UPDATED_PASSENGER_ID = 2L;
    public static final String DEFAULT_NAME = "a";
    public static final String UPDATED_NAME = "b";
    public static final String DEFAULT_EMAIL = "a@mail.com";
    public static final String UPDATED_EMAIL = "b@mail.com";
    public static final String INVALID_EMAIL = "invalid-email";
    public static final String DEFAULT_PHONE_NUMBER = "+375291234567";
    public static final String UPDATED_PHONE_NUMBER = "+375292345678";
    public static final String INVALID_PHONE_NUMBER = "invalid-phone-number";

    public Passenger getDefaultPassenger() {
        return Passenger.builder()
                .id(DEFAULT_PASSENGER_ID)
                .name(DEFAULT_NAME)
                .email(DEFAULT_EMAIL)
                .phoneNumber(DEFAULT_PHONE_NUMBER)
                .build();
    }

    public Passenger getUpdatedPassenger() {
        return Passenger.builder()
                .id(UPDATED_PASSENGER_ID)
                .name(UPDATED_NAME)
                .email(UPDATED_EMAIL)
                .phoneNumber(UPDATED_PHONE_NUMBER)
                .build();
    }

    public Passenger getPassengerWithInvalidEmail() {
        return Passenger.builder()
                .name(DEFAULT_NAME)
                .email(INVALID_EMAIL)
                .phoneNumber(DEFAULT_PHONE_NUMBER)
                .build();
    }

    public Passenger getPassengerWithEmailOnly() {
        return Passenger.builder()
                .id(UPDATED_PASSENGER_ID)
                .email(DEFAULT_EMAIL)
                .build();
    }

    public Passenger getPassengerWithInvalidPhoneNumber() {
        return Passenger.builder()
                .name(DEFAULT_NAME)
                .email(DEFAULT_EMAIL)
                .phoneNumber(INVALID_PHONE_NUMBER)
                .build();
    }

    public Passenger getPassengerWithPhoneNumberOnly() {
        return Passenger.builder()
                .id(UPDATED_PASSENGER_ID)
                .phoneNumber(DEFAULT_PHONE_NUMBER)
                .build();
    }

    public PassengerRequest getDefaultPassengerRequest() {
        return PassengerRequest.builder()
                .name(DEFAULT_NAME)
                .email(DEFAULT_EMAIL)
                .phoneNumber(DEFAULT_PHONE_NUMBER)
                .build();
    }

    public PassengerRequest getUpdatedPassengerRequest() {
        return PassengerRequest.builder()
                .name(UPDATED_NAME)
                .email(UPDATED_EMAIL)
                .phoneNumber(UPDATED_PHONE_NUMBER)
                .build();
    }

    public PassengerRequest getDefaultPassengerRequestWithInvalidEmail() {
        return PassengerRequest.builder()
                .name(DEFAULT_NAME)
                .email(INVALID_EMAIL)
                .phoneNumber(DEFAULT_PHONE_NUMBER)
                .build();
    }

    public PassengerRequest getDefaultPassengerRequestWithInvalidPhoneNumber() {
        return PassengerRequest.builder()
                .name(DEFAULT_NAME)
                .email(DEFAULT_EMAIL)
                .phoneNumber(INVALID_PHONE_NUMBER)
                .build();
    }

    public PassengerResponse getDefaultPassengerResponse() {
        return PassengerResponse.builder()
                .id(DEFAULT_PASSENGER_ID)
                .name(DEFAULT_NAME)
                .email(DEFAULT_EMAIL)
                .phoneNumber(DEFAULT_PHONE_NUMBER)
                .build();
    }

    public PassengerResponse getUpdatedPassengerResponse() {
        return PassengerResponse.builder()
                .id(UPDATED_PASSENGER_ID)
                .name(UPDATED_NAME)
                .email(UPDATED_EMAIL)
                .phoneNumber(UPDATED_PHONE_NUMBER)
                .build();
    }

}
