package com.ibm.fscc.employeeservice.exception;

/**
 * Custom exception class to indicate that an employee was not found.
 */
public class EmployeeNotFoundException extends RuntimeException{

	/**
     * Constructs a new {@link EmployeeNotFoundException} with the specified detail message and cause.
     *
     * @param eMessage  the detail message of the exception
     * @param exception the cause of the exception
     */
    public EmployeeNotFoundException(String eMessage, Exception exception) {
        super(eMessage, exception);
    }

    /**
     * Constructs a new {@link EmployeeNotFoundException} with the specified detail message.
     *
     * @param eMessage the detail message of the exception
     */
    public EmployeeNotFoundException(String eMessage) {
        super(eMessage);
    }
}
