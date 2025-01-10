package com.software.modsen.authservice.service.impl;

import com.software.modsen.authservice.dto.request.AuthDriverRequest;
import com.software.modsen.authservice.dto.request.LoginRequest;
import com.software.modsen.authservice.dto.request.AuthPassengerRequest;
import com.software.modsen.authservice.dto.request.RegisterRequest;
import com.software.modsen.authservice.exception.InvalidResourceException;
import com.software.modsen.authservice.exception.KeycloakUserCreationException;
import com.software.modsen.authservice.exception.WrongCredentialsException;
import com.software.modsen.authservice.service.AuthService;
import com.software.modsen.authservice.service.producer.AuthDriverProducer;
import com.software.modsen.authservice.service.producer.AuthPassengerProducer;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

import static com.software.modsen.authservice.utility.Constant.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\+375\\d{9}$");
    private static final Pattern VEHICLE_NUMBER_PATTERN = Pattern.compile("^\\d{4}[A-Z]{2}[1-7]$");
    private final AuthPassengerProducer passengerProducer;
    private final AuthDriverProducer driverProducer;

    @Value("${keycloak.server-url}")
    private String serverUrl;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.client-id}")
    private String clientId;
    @Value("${keycloak.secret}")
    private String clientSecret;
    @Value("${keycloak.username}")
    private String adminUsername;
    @Value("${keycloak.password}")
    private String adminPassword;

    private Keycloak keycloak;

    @PostConstruct
    public void initKeycloak() {
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(adminUsername)
                .password(adminPassword)
                .build();
    }

    @Override
    public AccessTokenResponse login(LoginRequest loginRequest) {
        try {
            return KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .grantType(OAuth2Constants.PASSWORD)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .username(loginRequest.getUsername())
                    .password(loginRequest.getPassword())
                    .build()
                    .tokenManager()
                    .getAccessToken();
        } catch (Exception e) {
            log.error(WRONG_CREDENTIALS);
            throw new WrongCredentialsException(e.getMessage());
        }
    }

    @Override
    public void register(RegisterRequest request) {
        try {
            RealmResource realmResource = keycloak.realm(realm);

            UserRepresentation user = new UserRepresentation();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setEnabled(true);
            user.setEmailVerified(true);
            user.singleAttribute("phoneNumber", request.getPhoneNumber());

            log.info(CREATING_USER);
            Response response = realmResource.users().create(user);

            if (response.getStatus() == 201) {
                String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

                UserResource userResource = realmResource.users().get(userId);
                CredentialRepresentation passwordCred = new CredentialRepresentation();
                passwordCred.setTemporary(false);
                passwordCred.setType(CredentialRepresentation.PASSWORD);
                passwordCred.setValue(request.getPassword());
                log.info(ASSIGNING_PASSWORD);
                userResource.resetPassword(passwordCred);

                RoleRepresentation role = realmResource.roles().get(request.getRole()).toRepresentation();
                log.info(ASSIGNING_ROLE);
                userResource.roles().realmLevel().add(Collections.singletonList(role));
            } else {
                throw new KeycloakUserCreationException(USER_CREATION_FAILURE + response.getStatusInfo().toString());
            }
        } catch (Exception e) {
            throw new KeycloakUserCreationException(e.getMessage());
        }
    }

    @Override
    public void registerPassenger(RegisterRequest request) {
        validateRegisterPassenger(request);
        request.setRole("PASSENGER");
        register(request);
        AuthPassengerRequest authPassengerRequest = AuthPassengerRequest.builder()
                .name(request.getUsername())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .action("CREATE")
                .build();
        passengerProducer.sendAuthPassengerMessage(authPassengerRequest);
    }

    @Override
    public void registerDriver(RegisterRequest request) {
        validateRegisterDriver(request);
        request.setRole("DRIVER");
        register(request);
        AuthDriverRequest authDriverRequest = AuthDriverRequest.builder()
                .name(request.getUsername())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .vehicleNumber(request.getVehicleNumber())
                .action("CREATE")
                .build();
        driverProducer.sendAuthDriverMessage(authDriverRequest);
    }

    private void validateRegisterPassenger(RegisterRequest request){
        validateEmail(request);
        validatePhoneNumber(request);
    }

    private void validateRegisterDriver(RegisterRequest request){
        validateEmail(request);
        validatePhoneNumber(request);
        validateVehicleNumber(request);
    }

    private void validateEmail (RegisterRequest request){
        if (!EMAIL_PATTERN.matcher(request.getEmail()).matches()) {
            throw new InvalidResourceException(String.format(INVALID_EMAIL, request.getEmail()));
        }
    }

    private void validatePhoneNumber (RegisterRequest request){
        if (!PHONE_NUMBER_PATTERN.matcher(request.getPhoneNumber()).matches()) {
            throw new InvalidResourceException(String.format(INVALID_PHONE_NUMBER, request.getPhoneNumber()));
        }
    }

    private void validateVehicleNumber(RegisterRequest request){
        if (!VEHICLE_NUMBER_PATTERN.matcher(request.getVehicleNumber()).matches()) {
            throw new InvalidResourceException(String.format(INVALID_VEHICLE_NUMBER, request.getVehicleNumber()));
        }
    }
}
