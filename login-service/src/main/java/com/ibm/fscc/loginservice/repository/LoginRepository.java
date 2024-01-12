package com.ibm.fscc.loginservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibm.fscc.loginservice.model.Login;

/**
 * Repository interface for managing login.
 */
@Repository
public interface LoginRepository extends CrudRepository<Login, String>{

	/**
	 * Retrieves an {@link Optional} containing the login entity with the specified email.
	 * @param email
	 * @return an Optional containing the user's login info, or empty if the email isn't found
	 */
	@Query(value = "SELECT * FROM Login WHERE email = :email", nativeQuery = true)
	Optional<Login> findByEmail(@Param("email") String email);
}
