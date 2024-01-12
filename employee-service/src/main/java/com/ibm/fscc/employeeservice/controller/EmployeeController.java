package com.ibm.fscc.employeeservice.controller;

import com.ibm.fscc.employeeservice.service.EmployeeService;
import com.ibm.fscc.employeeservice.dto.EmployeeDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class provides endpoints to perform CRUD (Create, Read, Update, Delete) operations on employees.
 * Endpoints are available for retrieving an employee by email, retrieving all employees,
 * creating a new employee, updating an existing employee, and deleting an employee by email.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/employee")
public class EmployeeController {

	private EmployeeService employeeService;

	/**
	 * Retrieves an employee by email.
	 *
	 * @param email the email address of the employee
	 * @return a {@link ResponseEntity} with the {@link EmployeeDTO} object if found,
	 *         or a {@link ResponseEntity} with a 404 status and a message if not found
	 */
	@GetMapping("/find")
	public ResponseEntity<?> findEmployeeByEmail(@RequestParam("email") String email) {
		return employeeService.findEmployeeByEmail(email);
	}

	/**
	 * Retrieves all employees from the employee repository.
	 *
	 * @return a {@link ResponseEntity} with a list of {@link EmployeeDTO} objects representing all employees
	 *         or a ResponseEntity with a 404 status and a message if no employees are found
	 */
	@GetMapping("/findAll")
	public ResponseEntity<?> findAllEmployees() {
		return employeeService.findAllEmployees();
	}

	/**
	 * Creates a new employee.
	 *
	 * @param employeeDTO the {@link EmployeeDTO} object containing the employee data
	 * @return a {@link ResponseEntity} with the created {@link EmployeeDTO} object if successful,
	 *         or a {@link ResponseEntity} with a 400 status and an error response body if the provided data is invalid,
	 *         or a {@link ResponseEntity} with a 409 status and a message if an employee with the same email already exists
	 */
	@PostMapping("/create")
	public ResponseEntity<?> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
		return employeeService.createEmployee(employeeDTO);
	}

	/**
	 * Updates an employee.
	 *
	 * @param employeeDTO the {@link EmployeeDTO} object containing the updated employee details
	 * @return a {@link ResponseEntity} with the updated {@link EmployeeDTO} object if successful,
	 *         or a {@link ResponseEntity} with a 400 status and an error response body if the provided data is invalid,
	 *         or a {@link ResponseEntity} with a 404 status and a message if the employee with the specified email is not found
	 */
	@PutMapping("/update")
	public ResponseEntity<?> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
		return employeeService.updateEmployee(employeeDTO);
	}

	/**
	 * Deletes an employee by email.
	 *
	 * @param email the email address of the employee to be deleted
	 * @return a {@link ResponseEntity} with a success message if the employee is deleted successfully,
	 *         or a {@link ResponseEntity} with a 404 status and a message if the employee with the specified email is not found
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteEmployee(@RequestParam("email") String email) {
		return employeeService.deleteEmployee(email);
	}
}
