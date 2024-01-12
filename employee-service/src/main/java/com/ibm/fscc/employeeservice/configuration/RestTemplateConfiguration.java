package com.ibm.fscc.employeeservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for creating a {@link RestTemplate} bean.
 * The {@link RestTemplate} bean can be used to simplify communication with RESTful web services.
 */
@Configuration
public class RestTemplateConfiguration {

	/**
     * Creates a {@link RestTemplate} bean.
     *
     * @return the {@link RestTemplate} bean instance
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
