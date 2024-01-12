package com.ibm.fscc.apigateway.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.List;

/**
 * Configuration class for Spring Security.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

	@Value("${jwt.public.key}")
    RSAPublicKey publicKey;

    /**
     * Configures the security filter chain for the application.
     *
     * @param http the {@link ServerHttpSecurity} object used to configure the security filter chain
     * @return the configured {@link SecurityWebFilterChain}
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .cors(corsSpec -> {
                    CorsConfigurationSource configurationSource = corsConfigurationSource();
                    corsSpec.configurationSource(configurationSource);
                })
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.GET, "/eureka/**")
                        .permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/login")
                        .permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/login/refreshToken")
                        .permitAll()
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
     * Creates and configures the {@link CorsConfigurationSource}.
     *
     * @return the {@link CorsConfigurationSource} with configured CORS settings
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name()
        ));
        configuration.setAllowedHeaders(Arrays.asList(HttpHeaders.CONTENT_TYPE, HttpHeaders.AUTHORIZATION));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        /* Registers the CorsConfiguration instance (configuration) for all URLs or
           URL patterns (/**). This means that the CORS configuration specified
           in configuration will be applied to all requests made to any URL in the application.
        */
        source.registerCorsConfiguration("/**", configuration);
        return source;
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
