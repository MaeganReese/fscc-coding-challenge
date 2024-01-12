package com.ibm.fscc.employeeservice.exception;

/**
 * Exception thrown when an employee already exists.
 */
public class EmployeeAlreadyExistsException extends RuntimeException{

	/**
     * Constructs a new {@link EmployeeAlreadyExistsException} with the specified error message and exception.
     *
     * @param eMessage   the error message
     * @param exception  the exception that caused this error
     */
    public EmployeeAlreadyExistsException(String eMessage, Exception exception) {
        super(eMessage, exception);
    }

    /**
     * Constructs a new {@link EmployeeAlreadyExistsException} with the specified error message.
     *
     * @param eMessage   the error message
     */
    public EmployeeAlreadyExistsException(String eMessage) {
        super(eMessage);
    }
}
