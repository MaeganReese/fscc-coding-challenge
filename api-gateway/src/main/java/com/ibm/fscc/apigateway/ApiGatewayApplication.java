package com.ibm.fscc.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * The main class for the API Gateway application.
 * It enables service discovery and serves as the entry point for the application.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ApiGatewayApplication {

	/**
	 * The main method that starts the API Gateway application.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
