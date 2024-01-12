package com.ibm.fscc.employeeservice.mapper;

import com.ibm.fscc.employeeservice.dto.EmployeeDTO;
import com.ibm.fscc.employeeservice.model.Employee;

/**
 * Mapper class for converting between {@link Employee} and {@link EmployeeDTO}.
 */
public class EmployeeMapper {

	/**
     * Converts an {@link Employee} entity to an {@link EmployeeDTO} object.
     *
     * @param employee the {@link Employee} entity to be converted
     * @return the corresponding {@link EmployeeDTO} object
     */
	public static EmployeeDTO employeeToEmployeeDTO(Employee employee) {
		EmployeeDTO employeeDto = new EmployeeDTO(
					employee.getEmail(),
					employee.getFirstName(),
					employee.getLastName(),
					employee.getAddress(),
					employee.getState(),
					employee.getZip(),
					employee.getCellPhone(),
					employee.getHomePhone()
				);
		
		return employeeDto;
	}
	
	/**
     * Converts an {@link EmployeeDTO} object to an {@link Employee} entity.
     *
     * @param employeeDTO the {@link EmployeeDTO} object to be converted
     * @return the corresponding {@link Employee} entity
     */
	public static Employee employeeDtoToEmployee(EmployeeDTO employeeDto) {
		Employee employee = new Employee(
					employeeDto.getEmail(),
					employeeDto.getFirstName(),
					employeeDto.getLastName(),
					employeeDto.getAddress(),
					employeeDto.getState(),
					employeeDto.getZip(),
					employeeDto.getCellPhone(),
					employeeDto.getHomePhone()
				);
		
		return employee;
	}
}
