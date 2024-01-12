package com.ibm.fscc.loginservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * Represents the RefreshToken entity in the database.
 * This class is annotated with Lombok annotations for getter, setter, and constructors.
 * It is also annotated with JPA annotations for mapping to the database table.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RefreshToken")
public class RefreshToken {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;

	 private String token;
	 private Instant createdDate;
}
