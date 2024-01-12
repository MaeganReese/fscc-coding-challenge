package com.ibm.fscc.loginservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LoginRequest is a Data Transfer Object (DTO) class representing the request for user login.
 * It holds data related to the user's login credentials, such as email and password.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

	@NotNull(message = "Email cannot be null.")
    @NotBlank(message = "Email is required.")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email format.")
    private String email;

    @NotNull(message = "Password cannot be null.")
    @NotBlank(message = "Password is required.")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;
}
