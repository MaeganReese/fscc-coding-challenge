package com.ibm.fscc.apigateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for defining routes in the API gateway.
 * This class configures the routes that map incoming requests to the corresponding services in the backend.
 */
@Configuration
public class GatewayConfiguration {

	/**
     * Creates a custom RouteLocator bean for defining routes.
     * The routes are configured based on the provided {@link RouteLocatorBuilder}.
     * Each route specifies the path pattern, filters to apply, and the target URI.
     *
     * @param builder the {@link RouteLocatorBuilder} used to define routes
     * @return the custom {@link RouteLocator} bean
     */
	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("login_route", r -> r.path("/api/login/**")
                        .uri("http://login-service:8080"))
				.route("employee_route", r -> r.path("/api/employee/**")
						.uri("http://employee-service:8081"))
				.route("discovery_route", r -> r.path("/eureka/**")
                        .uri("http://discovery-service:8761"))
				.build();
	}
}
