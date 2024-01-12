package com.ibm.fscc.employeeservice.service;

import com.ibm.fscc.employeeservice.exception.ErrorResponse;
import com.ibm.fscc.employeeservice.exception.EmployeeAlreadyExistsException;
import com.ibm.fscc.employeeservice.exception.EmployeeNotFoundException;
import com.ibm.fscc.employeeservice.exception.InvalidDataException;
import com.ibm.fscc.employeeservice.mapper.EmployeeMapper;
import com.ibm.fscc.employeeservice.model.Employee;
import com.ibm.fscc.employeeservice.repository.EmployeeRepository;
import com.ibm.fscc.employeeservice.dto.EmployeeDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing employee-related operations.
 */
@Service
@AllArgsConstructor
@Transactional
public class EmployeeService {

	private final EmployeeRepository employeeRepository;

    /**
     * Retrieves an employee by email.
     *
     * @param email the email address of the employee
     * @return a {@link ResponseEntity} with the {@link EmployeeDTO} object if found,
     *         or a {@link ResponseEntity} with a 404 status and a message if not found
     */
    public ResponseEntity<?> findEmployeeByEmail(String email) {
        try {
            Optional<Employee> employeeOptional = employeeRepository.findByEmail(email);

            if (employeeOptional.isEmpty())
                throw new EmployeeNotFoundException("Employee with email " + email + " not found.");

            Employee employee = employeeOptional.get();
            return ResponseEntity.status(HttpStatus.OK).body(EmployeeMapper.employeeToEmployeeDTO(employee));
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    /**
     * Retrieves all employees from the employee repository.
     *
     * @return a {@link ResponseEntity} with a list of {@link EmployeeDTO} objects representing all employees
     *         or a ResponseEntity with a 404 status and a message if no employees are found
     */
    public ResponseEntity<?> findAllEmployees() {
        try {
            Iterable<Employee> employeeIterable = employeeRepository.findAll();
            List<EmployeeDTO> employeeDTOList = new ArrayList<>();

            if (!employeeIterable.iterator().hasNext())
                throw new EmployeeNotFoundException("No employees found.");

            employeeIterable.forEach(employee -> {
                EmployeeDTO employeeDTO = EmployeeMapper.employeeToEmployeeDTO(employee);
                employeeDTOList.add(employeeDTO);
            });

            return ResponseEntity.status(HttpStatus.OK).body(employeeDTOList);
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Creates a new employee.
     *
     * @param employeeDTO the {@link EmployeeDTO} object containing the employee data
     * @return a {@link ResponseEntity} with the created {@link EmployeeDTO} object if successful,
     *         or a {@link ResponseEntity} with a 400 status and an error response body if the provided data is invalid,
     *         or a {@link ResponseEntity} with a 409 status and a message if an employee with the same email already exists
     */
    public ResponseEntity<?> createEmployee(EmployeeDTO employeeDTO) {
        try {
            // Validate employee object
            validate(employeeDTO);

            // Check if an employee with the same email address exists
            Optional<Employee> employeeOptional = employeeRepository.findByEmail(employeeDTO.getEmail());

            if (employeeOptional.isEmpty())
                throw new EmployeeNotFoundException("Employee with email " + employeeDTO.getEmail() + " not found.");

            throw new EmployeeAlreadyExistsException("Employee with email " + employeeDTO.getEmail() + " already exists.");
        } catch (InvalidDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getErrorResponse().getErrors());
        } catch (EmployeeAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EmployeeNotFoundException e) {
            // Employee not found, proceed with creating a new employee
            Employee newEmployee = EmployeeMapper.employeeDtoToEmployee(employeeDTO);
            Employee savedEmployee = employeeRepository.save(newEmployee);

            return ResponseEntity.status(HttpStatus.CREATED).body(EmployeeMapper.employeeToEmployeeDTO(savedEmployee));
        }
    }

    /**
     * Updates an employee.
     *
     * @param employeeDTO the {@link EmployeeDTO} object containing the updated employee details
     * @return a {@link ResponseEntity} with the updated {@link EmployeeDTO} object if successful,
     *         or a {@link ResponseEntity} with a 400 status and an error response body if the provided data is invalid,
     *         or a {@link ResponseEntity} with a 404 status and a message if the employee with the specified email is not found
     */
    public ResponseEntity<?> updateEmployee(EmployeeDTO employeeDTO) {
        try {
            // Validate employee object
            validate(employeeDTO);

            // Retrieve the existing employee by email
            Optional<Employee> existingEmployeeOptional = employeeRepository.findByEmail(employeeDTO.getEmail());
            if (existingEmployeeOptional.isEmpty())
                throw new EmployeeNotFoundException("Employee with email " + employeeDTO.getEmail() + " not found.");

            // Map the existing EmployeeDTO to an Employee entity
            Employee existingEmployee = existingEmployeeOptional.get();

            // Update the employee details with the values from the provided EmployeeDTO
            existingEmployee.setFirstName(employeeDTO.getFirstName());
            existingEmployee.setLastName(employeeDTO.getLastName());
            existingEmployee.setAddress(employeeDTO.getAddress());
            existingEmployee.setState(employeeDTO.getState());
            existingEmployee.setZip(employeeDTO.getZip());
            existingEmployee.setCellPhone(employeeDTO.getCellPhone());
            existingEmployee.setHomePhone(employeeDTO.getHomePhone());

            // Save the updated employee
            Employee updatedEmployee = employeeRepository.save(existingEmployee);

            // Map the updated Employee entity back to an EmployeeDTO
            return ResponseEntity.status(HttpStatus.OK).body(EmployeeMapper.employeeToEmployeeDTO(updatedEmployee));
        } catch (InvalidDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getErrorResponse().getErrors());
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with email " + employeeDTO.getEmail() + " could not be found.");
        }
    }

    /**
     * Deletes an employee by email.
     *
     * @param email the email address of the employee to be deleted
     * @return a {@link ResponseEntity} with a success message if the employee is deleted successfully,
     *         or a {@link ResponseEntity} with a 404 status and a message if the employee with the specified email is not found
     */
    public ResponseEntity<?> deleteEmployee(String email) {
        try {
            // Retrieve the employee to be deleted. If the employee is not found, throw an exception.
            Optional<Employee> employeeOptional = employeeRepository.findByEmail(email);

            if (employeeOptional.isPresent()) {
                Employee employee = employeeOptional.get();
                employeeRepository.delete(employee);
                return ResponseEntity.ok("Employee deleted successfully.");
            } else {
                throw new EmployeeNotFoundException("Employee with email " + email + " could not be found.");
            }
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Validates an object for any invalid data using Bean Validation annotations.
     *
     * @param object the {@link Object} to be validated
     * @param <T>    the type of the object
     * @throws InvalidDataException if there are any validation errors in the object
     */
    public static <T> void validate(T object) {
        try(ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<T>> violations = validator.validate(object);

            if (!violations.isEmpty()) {
                Map<String, String> errorMap = violations.stream()
                        .collect(Collectors.toMap(
                                violation -> violation.getPropertyPath().toString(),
                                ConstraintViolation::getMessage
                        ));

                throw new InvalidDataException(new ErrorResponse(errorMap));
            }
        }
    }

}
