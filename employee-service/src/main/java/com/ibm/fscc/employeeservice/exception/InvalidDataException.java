package com.ibm.fscc.employeeservice.exception;

/**
 * Exception thrown when the provided DTO data is invalid.
 */
public class InvalidDataException extends RuntimeException{

	/**
     * The error response associated with the exception.
     */
    private final ErrorResponse errorResponse;

    /**
     * Constructs an {@link InvalidDataException} with the given error response.
     *
     * @param errorResponse The error response containing information about the invalid data.
     */
    public InvalidDataException(ErrorResponse errorResponse) {
        super(errorResponse.getErrors().toString());
        this.errorResponse = errorResponse;
    }

    /**
     * Retrieves the error response associated with the exception.
     *
     * @return The {@link ErrorResponse} containing information about the invalid data.
     */
    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
