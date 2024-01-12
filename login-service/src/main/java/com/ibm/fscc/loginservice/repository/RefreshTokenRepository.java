package com.ibm.fscc.loginservice.repository;

import com.ibm.fscc.loginservice.model.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing refresh tokens.
 */
@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

	/**
     * Retrieves an {@link Optional} containing the refresh token entity with the specified token.
     *
     * @param token the token to search for
     * @return an Optional containing the refresh token entity, or an empty Optional if not found
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * Deletes the refresh token entity with the specified token.
     *
     * @param token the token to delete
     */
    void deleteByToken(String token);
}
