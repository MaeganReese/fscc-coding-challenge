package com.ibm.fscc.loginservice.exception;

import java.util.Map;

/**
 * ErrorResponse is a class representing an error response containing a map of error messages.
 * This class is typically used to encapsulate error details and provide a standardized format for returning errors to clients.
 */
public class ErrorResponse {

	private final Map<String, String> errors;

    /**
     * Constructs an ErrorResponse with the provided error map.
     *
     * @param errors the {@link Map} containing error keys and their corresponding error messages
     */
    public ErrorResponse(Map<String, String> errors) {
        this.errors = errors;
    }

    /**
     * Retrieves the map of error messages from the ErrorResponse.
     *
     * @return the {@link Map} containing error keys and their corresponding error messages
     */
    public Map<String, String> getErrors() {
        return errors;
    }
}
