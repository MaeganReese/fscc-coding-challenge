package com.ibm.fscc.loginservice.mapper;

import com.ibm.fscc.loginservice.dto.LoginRequest;
import com.ibm.fscc.loginservice.model.Login;

/**
 * The Login Mapper class is for mapping between the {@link Login} entity and the {@link LoginRequest} DTO
 */
public class LoginMapper {

	/**
	 *  Maps a {@link LoginRequest} object to a {@link Login} entity.
	 * 
	 * @param loginRequest The {@link LoginRequest} object to be mapped.
	 * @return The mapped {@link Login} entity
	 */
	public static Login loginRequestToLogin(LoginRequest loginRequest) {
		Login login = new Login(loginRequest.getEmail(), loginRequest.getPassword());
		return login;
	}
	
	/**
     * Maps a {@link Login} entity to a {@link LoginRequest} object.
     *
     * @param login The {@link Login} entity to be mapped.
     * @return The mapped {@link LoginRequest} object.
     */
	public static LoginRequest loginToLoginRequest(Login login) {
		LoginRequest loginRequest = new LoginRequest(login.getEmail(), login.getPassword());
		return loginRequest;
	}
}
