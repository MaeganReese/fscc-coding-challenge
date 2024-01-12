package com.ibm.fscc.loginservice.service;

import com.ibm.fscc.loginservice.exception.InvalidRefreshTokenException;
import com.ibm.fscc.loginservice.model.RefreshToken;
import com.ibm.fscc.loginservice.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

/**
 * Service class for managing refresh tokens.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;

    /**
     * Generates a new refresh token.
     *
     * @return the generated refresh token
     */
    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }
    
    /**
     * Validates the given refresh token.
     *
     * @param token the refresh token to validate
     * @throws InvalidRefreshTokenException if the {@link RefreshToken} is invalid
     */
    public void validateRefreshToken(String token) {
        refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new InvalidRefreshTokenException("Invalid refresh token."));
    }

    /**
     * Deletes the refresh token with the given token.
     *
     * @param token the token of the refresh token to delete
     * @throws InvalidRefreshTokenException if the {@link RefreshToken} does not exist
     */
    public void deleteRefreshToken(String token) throws InvalidRefreshTokenException {
        validateRefreshToken(token);
        refreshTokenRepository.deleteByToken(token);
    }
}
