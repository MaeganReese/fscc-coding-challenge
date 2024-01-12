package com.ibm.fscc.loginservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ibm.fscc.loginservice.dto.LoginRequest;
import com.ibm.fscc.loginservice.exception.InvalidDataException;
import com.ibm.fscc.loginservice.service.LoginService;

import lombok.AllArgsConstructor;

/**
 * Main class for the Login Service application
 */
@AllArgsConstructor
@EnableDiscoveryClient
@SpringBootApplication
public class LoginServiceApplication implements CommandLineRunner{

	private final LoginService loginService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	/**
	 * Main method to start the Login Service application.
	 *
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(LoginServiceApplication.class, args);
	}

	/**
	 * Creates a user in the login database upon starting
	 * 
	 */
	@Override
	public void run(String... args) throws Exception {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail("test12345@test.com");
		loginRequest.setPassword(bCryptPasswordEncoder.encode("test1234"));
		
		try {
			loginService.saveLoginInformation(loginRequest);
		}catch(InvalidDataException e) {
			System.out.println(e.getMessage());
		}
	}

}
