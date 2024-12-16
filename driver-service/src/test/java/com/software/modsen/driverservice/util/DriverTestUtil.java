package com.software.modsen.driverservice.util;

import com.software.modsen.driverservice.dto.request.DriverRequest;
import com.software.modsen.driverservice.dto.response.DriverResponse;
import com.software.modsen.driverservice.model.Driver;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DriverTestUtil {
    public static final Long DEFAULT_DRIVER_ID = 1L;
    public static final Long UPDATED_DRIVER_ID = 2L;
    public static final String DEFAULT_NAME = "a";
    public static final String UPDATED_NAME = "b";
    public static final String DEFAULT_EMAIL = "a@mail.com";
    public static final String UPDATED_EMAIL = "b@mail.com";
    public static final String INVALID_EMAIL = "invalid-email";
    public static final String DEFAULT_PHONE_NUMBER = "+375291234567";
    public static final String UPDATED_PHONE_NUMBER = "+375292345678";
    public static final String INVALID_PHONE_NUMBER = "invalid-phone-number";
    public static final String DEFAULT_VEHICLE_NUMBER = "1234AB1";
    public static final String UPDATED_VEHICLE_NUMBER = "5678CD5";
    public static final String INVALID_VEHICLE_NUMBER = "invalid-vehicle-number";

    public Driver getDefaultDriver() {
        return Driver.builder()
                .id(DEFAULT_DRIVER_ID)
                .name(DEFAULT_NAME)
                .email(DEFAULT_EMAIL)
                .phoneNumber(DEFAULT_PHONE_NUMBER)
                .vehicleNumber(DEFAULT_VEHICLE_NUMBER)
                .build();
    }

    public Driver getUpdatedDriver() {
        return Driver.builder()
                .id(UPDATED_DRIVER_ID)
                .name(UPDATED_NAME)
                .email(UPDATED_EMAIL)
                .phoneNumber(UPDATED_PHONE_NUMBER)
                .vehicleNumber(UPDATED_VEHICLE_NUMBER)
                .build();
    }

    public Driver getDriverWithInvalidEmail() {
        return Driver.builder()
                .name(DEFAULT_NAME)
                .email(INVALID_EMAIL)
                .phoneNumber(DEFAULT_PHONE_NUMBER)
                .vehicleNumber(DEFAULT_VEHICLE_NUMBER)
                .build();
    }

    public Driver getDriverWithEmailOnly() {
        return Driver.builder()
                .id(UPDATED_DRIVER_ID)
                .email(DEFAULT_EMAIL)
                .build();
    }

    public Driver getDriverWithInvalidPhoneNumber() {
        return Driver.builder()
                .name(DEFAULT_NAME)
                .email(DEFAULT_EMAIL)
                .phoneNumber(INVALID_PHONE_NUMBER)
                .vehicleNumber(DEFAULT_VEHICLE_NUMBER)
                .build();
    }

    public Driver getDriverWithPhoneNumberOnly() {
        return Driver.builder()
                .id(UPDATED_DRIVER_ID)
                .phoneNumber(DEFAULT_PHONE_NUMBER)
                .build();
    }

    public Driver getDriverWithInvalidVehicleNumber() {
        return Driver.builder()
                .name(DEFAULT_NAME)
                .email(DEFAULT_EMAIL)
                .phoneNumber(DEFAULT_PHONE_NUMBER)
                .vehicleNumber(INVALID_VEHICLE_NUMBER)
                .build();
    }

    public Driver getDriverWithVehicleNumberOnly() {
        return Driver.builder()
                .id(UPDATED_DRIVER_ID)
                .vehicleNumber(DEFAULT_VEHICLE_NUMBER)
                .build();
    }

    public DriverRequest getDefaultDriverRequest() {
        return DriverRequest.builder()
                .name(DEFAULT_NAME)
                .email(DEFAULT_EMAIL)
                .phoneNumber(DEFAULT_PHONE_NUMBER)
                .vehicleNumber(DEFAULT_VEHICLE_NUMBER)
                .build();
    }

    public DriverRequest getUpdatedDriverRequest() {
        return DriverRequest.builder()
                .name(UPDATED_NAME)
                .email(UPDATED_EMAIL)
                .phoneNumber(UPDATED_PHONE_NUMBER)
                .vehicleNumber(UPDATED_VEHICLE_NUMBER)
                .build();
    }

    public DriverRequest getDefaultDriverRequestWithInvalidEmail() {
        return DriverRequest.builder()
                .name(DEFAULT_NAME)
                .email(INVALID_EMAIL)
                .phoneNumber(DEFAULT_PHONE_NUMBER)
                .vehicleNumber(DEFAULT_VEHICLE_NUMBER)
                .build();
    }

    public DriverRequest getDefaultDriverRequestWithInvalidPhoneNumber() {
        return DriverRequest.builder()
                .name(DEFAULT_NAME)
                .email(DEFAULT_EMAIL)
                .phoneNumber(INVALID_PHONE_NUMBER)
                .vehicleNumber(DEFAULT_VEHICLE_NUMBER)
                .build();
    }

    public DriverRequest getDefaultDriverRequestWithInvalidVehicleNumber() {
        return DriverRequest.builder()
                .name(DEFAULT_NAME)
                .email(DEFAULT_EMAIL)
                .phoneNumber(DEFAULT_PHONE_NUMBER)
                .vehicleNumber(INVALID_VEHICLE_NUMBER)
                .build();
    }

    public DriverResponse getDefaultDriverResponse() {
        return DriverResponse.builder()
                .id(DEFAULT_DRIVER_ID)
                .name(DEFAULT_NAME)
                .email(DEFAULT_EMAIL)
                .phoneNumber(DEFAULT_PHONE_NUMBER)
                .vehicleNumber(DEFAULT_VEHICLE_NUMBER)
                .build();
    }

    public DriverResponse getUpdatedDriverResponse() {
        return DriverResponse.builder()
                .id(UPDATED_DRIVER_ID)
                .name(UPDATED_NAME)
                .email(UPDATED_EMAIL)
                .phoneNumber(UPDATED_PHONE_NUMBER)
                .vehicleNumber(UPDATED_VEHICLE_NUMBER)
                .build();
    }

}
