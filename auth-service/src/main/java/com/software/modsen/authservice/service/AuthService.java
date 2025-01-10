package com.software.modsen.authservice.service;

import com.software.modsen.authservice.dto.request.LoginRequest;
import com.software.modsen.authservice.dto.request.RegisterRequest;
import org.keycloak.representations.AccessTokenResponse;

public interface AuthService {
    AccessTokenResponse login(LoginRequest loginRequest);
    void register(RegisterRequest request);
    void registerPassenger(RegisterRequest request);
    void registerDriver(RegisterRequest request);
}
