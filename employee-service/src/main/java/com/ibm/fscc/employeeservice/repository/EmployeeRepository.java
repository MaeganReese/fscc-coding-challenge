package com.ibm.fscc.employeeservice.repository;

import com.ibm.fscc.employeeservice.model.Employee;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing employee entities.
 */
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long>{

	/**
     * Finds an employee by their email.
     *
     * @param email the email address of the employee
     * @return an Optional containing the Employee if found, or an empty Optional if not found
     */
    @Query(value = "SELECT * FROM Employee WHERE email = :email", nativeQuery = true)
    Optional<Employee> findByEmail(@Param("email") String email);

    /**
     * Saves an employee to the repository.
     *
     * @param employee the Employee object to be saved
     * @param <S>      the type of the Employee object
     * @return the saved Employee object
     */
    <S extends Employee> @NotNull S save(@NotNull S employee);

}
