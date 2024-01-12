package com.ibm.fscc.discoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Main class for the Discovery Service application.
 */
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServiceApplication {

	/**
	 * Main method to start the Discovery Service application.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(DiscoveryServiceApplication.class, args);
	}

}
