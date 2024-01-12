package com.ibm.fscc.loginservice.exception;

/**
 * Exception thrown when an employee already exists.
 */
public class UserAlreadyExistsException extends RuntimeException{

	/**
     * Constructs a new {@link EmployeeAlreadyExistsException} with the specified error message and exception.
     *
     * @param eMessage   the error message
     * @param exception  the exception that caused this error
     */
    public UserAlreadyExistsException(String eMessage, Exception exception) {
        super(eMessage, exception);
    }

    /**
     * Constructs a new {@link EmployeeAlreadyExistsException} with the specified error message.
     *
     * @param eMessage   the error message
     */
    public UserAlreadyExistsException(String eMessage) {
        super(eMessage);
    }
}
