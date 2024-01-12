package com.ibm.fscc.loginservice.exception;

/**
 * Exception thrown when a refresh token is invalid.
 */
public class InvalidRefreshTokenException extends RuntimeException{

	/**
     * Constructs a new {@link InvalidRefreshTokenException} with the specified error message and exception.
     *
     * @param eMessage   the error message
     * @param exception  the exception that caused this error
     */
    public InvalidRefreshTokenException(String eMessage, Exception exception) {
        super(eMessage, exception);
    }

    /**
     * Constructs a new {@link InvalidRefreshTokenException} with the specified error message.
     *
     * @param eMessage   the error message
     */
    public InvalidRefreshTokenException(String eMessage) {
        super(eMessage);
    }
}
