package com.ibm.fscc.loginservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RefreshTokenRequest is a Data Transfer Object (DTO) class representing the request for refreshing an authentication token.
 * It holds data related to the refresh token and the associated user's email.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest {

	private String refreshToken;
    private String email;
}
