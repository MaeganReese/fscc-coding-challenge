package com.ibm.fscc.loginservice.service;

import com.ibm.fscc.loginservice.configuration.LoginAuthentication;
import com.ibm.fscc.loginservice.dto.*;
import com.ibm.fscc.loginservice.exception.UserAlreadyExistsException;
import com.ibm.fscc.loginservice.exception.UserNotFoundException;
import com.ibm.fscc.loginservice.mapper.LoginMapper;
import com.ibm.fscc.loginservice.model.Login;
import com.ibm.fscc.loginservice.repository.LoginRepository;
import com.ibm.fscc.loginservice.util.JwtProvider;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.Instant;
import java.util.Optional;

/**
 * Service class for managing login operations.
 */
@Service
@AllArgsConstructor
public class LoginService {

	private final JwtProvider jwtProvider;
	private final RefreshTokenService refreshTokenService;
	private final LoginAuthentication loginAuthentication;
	private final LoginRepository loginRepository;
	
	/**
	 * Performs the login operation for the given login request and returns the login response.
	 *
	 * @param loginRequest the {@link LoginRequest} containing the email and password for login
	 * @return the {@link LoginResponse} with the authentication token, refresh token, expiration time,
	 *         and admin information if available
	 */
	public ResponseEntity<?> login(LoginRequest loginRequest) {
		try {
			Authentication authentication = loginAuthentication.authenticate(
					new UsernamePasswordAuthenticationToken(
							loginRequest.getEmail(),
							loginRequest.getPassword()
					)
			);

			// The SecurityContextHolder is where Spring Security stores the details of who is authenticated.
			// getContext() - obtains the current SecurityContext.
			// setAuthentication(Authentication authentication) - changes the currently authenticated principal,
			// or removes the authentication information.
			SecurityContextHolder.getContext().setAuthentication(authentication);

			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			httpHeaders.add("X-Service-Id", "login-service");

			MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
			queryParams.add("email", loginRequest.getEmail());

			return ResponseEntity.status(HttpStatus.OK).body(
					LoginResponse.builder()
						.authenticationToken(jwtProvider.generateToken(authentication))
						.expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
						.build());
		} catch (UserNotFoundException | AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error.");
		}
	}
	
	/**
	 * Saves the user's login information.
	 *
	 * @param loginRequest the {@link LoginRequest} object containing the user's login information
	 * @throws DataIntegrityViolationException if there is a data integrity violation when saving the login information
	 */
	public ResponseEntity<String> saveLoginInformation(LoginRequest loginRequest) {
		try {
			String email = loginRequest.getEmail();
			Optional<Login> loginOptional = loginRepository.findByEmail(email);
			
			if(loginOptional.isEmpty()) {
				throw new UserNotFoundException("User with email " + email + " not found.");
			}
			
			throw new UserAlreadyExistsException("User with email " + email + " already exists.");
		} catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch(UserNotFoundException e) {
			loginRepository.save(LoginMapper.loginRequestToLogin(loginRequest));
			return ResponseEntity.status(HttpStatus.OK).body("Login information saved successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error.");
		}
	}
}
