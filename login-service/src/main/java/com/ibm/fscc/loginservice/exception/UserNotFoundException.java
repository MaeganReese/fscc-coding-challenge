package com.ibm.fscc.loginservice.exception;

/**
 * Exception thrown when a user is not found
 */
public class UserNotFoundException extends RuntimeException {

	/**
     * Constructs a new {@link AdminNotFoundException} with the specified error message and exception.
     *
     * @param eMessage   the error message
     * @param exception  the exception that caused this error
     */
    public UserNotFoundException(String eMessage, Exception exception) {
        super(eMessage, exception);
    }

    /**
     * Constructs a new {@link AdminNotFoundException} with the specified error message.
     *
     * @param eMessage   the error message
     */
    public UserNotFoundException(String eMessage) {
        super(eMessage);
    }
}
