package com.ibm.fscc.employeeservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;

import java.security.interfaces.RSAPublicKey;

/**
 * Configuration class for Spring Security.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

	@Value("${jwt.public.key}")
    RSAPublicKey publicKey;

    /**
     * Configures the security filter chain for the registration-service.
     *
     * @param http the {@link ServerHttpSecurity} object used to configure the security filter chain
     * @return the configured {@link SecurityWebFilterChain}
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchanges -> exchanges
                        .anyExchange()
                        .authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtDecoder(jwtDecoder())))
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler())
                )
                .build();
    }

    /**
     * Custom authentication entry point.
     * Handles unauthorized access and sets the appropriate HTTP status code.
     *
     * @return the {@link ServerAuthenticationEntryPoint} instance to handle unauthorized access
     */
    @Bean
    public ServerAuthenticationEntryPoint authenticationEntryPoint() {
        return (exchange, exception) -> {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        };
    }

    /**
     * Custom access denied handler.
     * Handles access denied cases and sets the appropriate HTTP status code.
     *
     * @return the {@link ServerAccessDeniedHandler} instance to handle access denied cases
     */
    @Bean
    public ServerAccessDeniedHandler accessDeniedHandler() {
        return (exchange, deniedException) -> {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        };
    }

    /**
     * Creates a {@link ReactiveJwtDecoder} bean for decoding JWT tokens.
     *
     * @return the {@link ReactiveJwtDecoder} bean configured with the public key
     */
    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return NimbusReactiveJwtDecoder.withPublicKey(this.publicKey).build();
    }
}
