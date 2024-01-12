package com.ibm.fscc.loginservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * LoginResponse is a Data Transfer Object (DTO) class representing the response for user login.
 * It holds data related to the login response, such as authentication token, refresh token, expiration time, and admin details.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

	private String authenticationToken;
    private String refreshToken;
    private Instant expiresAt;
}
