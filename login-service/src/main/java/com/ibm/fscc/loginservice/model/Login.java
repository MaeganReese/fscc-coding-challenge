package com.ibm.fscc.loginservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a login entity in the database
 * This class is annotated with Lombok annotations for getter, setter, and constructors.
 * It is also annotated with JPA annotations for mapping to the database table.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="login")
public class Login {
	
	@Id
	@Column(unique=true)
	@NotBlank(message="Email is required.")
	private String email;
	
	@NotBlank(message="Password is required.")
	private String password;
	
}