package com.ibm.fscc.loginservice.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Service class for JWT (JSON Web Token) related operations.
 */
@Service
@RequiredArgsConstructor
public class JwtProvider {

	private final JwtEncoder jwtEncoder;

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;
    
    /**
     * Generates a JWT token based on the provided authentication.
     *
     * @param authentication the {@link Authentication} object
     * @return the generated JWT token
     */
    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        System.out.println(principal.getUsername());
        return generateTokenWithEmail(principal.getUsername());
    }

    /**
     * Generates a JWT token based on the provided email.
     *
     * @param email the email to be included in the token
     * @return the generated JWT token
     */
    public String generateTokenWithEmail(String email) {
        // The JWT Claims Set is a JSON object representing the claims conveyed by a JSON Web Token.
        // issuer(String issuer) - Sets the issuer (iss) claim, which identifies the principal that issued the JWT.
        // issuedAt(Instant issuedAt) - Sets the issued at (iat) claim, which identifies the time at which the JWT was issued.
        // expiresAt(Instant expiresAt) - Sets the expiration time (exp) claim, which identifies the time on
        // or after which the JWT MUST NOT be accepted for processing.
        // claim(String name, Object value) - Sets the claim.
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusMillis(jwtExpirationInMillis))
                .subject(email)
                .claim("scope", "ROLE_USER")
                .build();

        // JwtEncoderParameters - a holder of parameters containing the JWS headers and JWT Claims Set.
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     * Retrieves the JWT expiration time in milliseconds.
     *
     * @return the JWT expiration time in milliseconds
     */
    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }
}
