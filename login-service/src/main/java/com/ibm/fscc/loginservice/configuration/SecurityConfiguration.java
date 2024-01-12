package com.ibm.fscc.loginservice.configuration;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import reactor.core.publisher.Mono;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

/**
 * Configuration class for Spring Security.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

	
	@Value("${jwt.public.key}")
    RSAPublicKey publicKey;

    @Value("${jwt.private.key}")
    RSAPrivateKey privateKey;
    
    /**
     * Configures the security filter chain for the login-service.
     *
     * @param http the {@link ServerHttpSecurity} object used to configure the security filter chain
     * @return the configured {@link SecurityWebFilterChain}
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(auth -> auth
                        .pathMatchers(HttpMethod.POST, "/api/login")
                        .permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/login/refreshToken")
                        .permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/login/save")
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
     * Configures the {@link PasswordEncoder} bean for password encoding.
     *
     * @return the {@link PasswordEncoder} bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
    
    /**
     * Creates a {@link JwtEncoder} bean for encoding JWT tokens.
     *
     * @return the {@link JwtEncoder} bean
     */
    @Bean
    public JwtEncoder jwtEncoder() {
        // JWK - The base abstract class for JSON Web Keys (JWKs). It serialises to a JSON object.
        // Create a JWK object using the provided publicKey and privateKey
        JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(this.privateKey).build();

        // JWKSource - Represents a source of JSON Web Keys with an associated security context.
        // Create a JWKSource with an ImmutableJWKSet containing the JWK
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));

        // NimbusJwtEncoder - An implementation of JwtEncoder that encodes JWTs using Nimbus JOSE+JWT library.
        // Create a new NimbusJwtEncoder using the JWKSource
        return new NimbusJwtEncoder(jwkSource);
    }
}
