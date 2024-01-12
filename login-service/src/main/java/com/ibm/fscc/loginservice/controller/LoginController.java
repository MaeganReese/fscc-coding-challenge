package com.ibm.fscc.loginservice.controller;

import com.ibm.fscc.loginservice.dto.LoginRequest;
import com.ibm.fscc.loginservice.dto.LoginResponse;
import com.ibm.fscc.loginservice.dto.RefreshTokenRequest;
import com.ibm.fscc.loginservice.service.LoginService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling login-related operations.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/login")
public class LoginController {

	private final LoginService loginService;

	/**
	 * Handles the login request.
	 *
	 * @param loginRequest the login request containing email and password.
	 * @return the {@link ResponseEntity} with a success response containing the {@link LoginResponse} object
	 * if login is successful, or an error response with an error message if login fails.
	 */
	@PostMapping
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		return loginService.login(loginRequest);
	}
	
	/**
	 * Handles saving the user's login information.
	 *
	 * @param loginRequest The {@link LoginRequest} object containing the user's login information.
	 * @return the {@link ResponseEntity} indicating the status of the operation.
	 *         If successful, it returns an empty body with status code 200 (OK).
	 *         If the login information conflicts with an existing admin's email,
	 *         it returns a message with status code 409 (Conflict) and an error message in the response body.
	 */
	@PostMapping("/save")
	public ResponseEntity<String> saveLogin(@RequestBody LoginRequest loginRequest) {
		return loginService.saveLoginInformation(loginRequest);
	}
	
}
