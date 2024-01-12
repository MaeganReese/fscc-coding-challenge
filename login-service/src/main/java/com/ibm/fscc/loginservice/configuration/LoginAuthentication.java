package com.ibm.fscc.loginservice.configuration;

import com.ibm.fscc.loginservice.exception.UserNotFoundException;
import com.ibm.fscc.loginservice.model.Login;
import com.ibm.fscc.loginservice.repository.LoginRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Authentication provider for handling login authentication.
 * It implements the {@link AuthenticationProvider} interface provided by Spring Security.
 */
@Service
@Primary
@RequiredArgsConstructor
public class LoginAuthentication implements AuthenticationProvider{
	
	private final LoginRepository loginRepository;
	private final PasswordEncoder passwordEncoder;
	
	/**
     * Authenticates the provided credentials.
     *
     * @param authentication the {@link Authentication} object containing user credentials
     * @return the authenticated {@link Authentication} object
     * @throws AuthenticationException if authentication fails
     */
    @Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	// Extract email and password from the authentication object
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Find the admin with the provided email
        Optional<Login> login = loginRepository.findByEmail(email);

        // Check if the admin exists
        if (login.isEmpty())
            throw new UserNotFoundException("Admin not found with email: " + email);

        Login user = login.get();

        // Compare the provided password with the stored password
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new BadCredentialsException("Invalid email or password");

        List<GrantedAuthority> authorities = new ArrayList<>();

        // Create a UserDetails object representing the authenticated user
        UserDetails userDetails = new User(email, password, authorities);

        return new UsernamePasswordAuthenticationToken(userDetails, password, authorities);
	}

    /**
     * Checks if this authentication provider supports the provided authentication type.
     *
     * @param authentication the {@link Class} representing the authentication type to check
     * @return true if this provider supports the authentication type, false otherwise
     */
    @Override
	public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
