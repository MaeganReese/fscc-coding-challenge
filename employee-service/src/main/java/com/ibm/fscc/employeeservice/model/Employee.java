package com.ibm.fscc.employeeservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an employee entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Employee")
public class Employee {

	@Id
	@Column(unique = true)
	private String email;
	
	private String firstName;
	private String lastName;
	private String address;
	private String state;
	private String zip;
	private String cellPhone;
	private String homePhone;
}

