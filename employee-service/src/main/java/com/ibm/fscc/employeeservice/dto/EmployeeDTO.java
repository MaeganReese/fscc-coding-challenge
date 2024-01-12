package com.ibm.fscc.employeeservice.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) for Employee.
 * Represents the data structure used for transferring employee information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    
	@NotNull(message = "Email cannot be null.")
    @NotBlank(message = "Email is required.")
    @Pattern(regexp = "[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}", message = "Invalid email format")
    private String email;
	
    @NotNull(message = "First name cannot be null.")
    @NotBlank(message = "First name is required.")
    @Pattern(regexp = "[A-Za-z ]{2,35}", message = "First name must be between 2 and 35 characters and contain only alphabetic characters and spaces")
    private String firstName;

    @NotNull(message = "Last name cannot be null.")
    @NotBlank(message = "Last name is required.")
    @Pattern(regexp = "[A-Za-z ]{2,35}", message = "Last name must be between 2 and 35 characters and contain only alphabetic characters and spaces")
    private String lastName;

    @NotNull(message = "Address cannot be null.")
    @NotBlank(message = "Address is required.")
    @Pattern(regexp = "[A-Za-z0-9 ]{10,50}", message = "Address must be between 10 and 50 characters and contain only alphanumeric characters and spaces")
    private String address;

    @NotNull(message = "State cannot be null.")
    @NotBlank(message = "State is required.")
    @Pattern(regexp = "[A-Z]{2}", message = "Use 2-letter uppercase state codes")
    private String state;

    @NotNull(message = "Zip code cannot be null.")
    @NotBlank(message = "Zip code is required.")
    @Pattern(regexp = "\\d{5}(-\\d{4})?", message = "Zip code should be in the format XXXXX or XXXXX-XXXX")
    private String zip;

    @NotNull(message = "Cell phone cannot be null.")
    @NotBlank(message = "Cell phone is required.")
    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}", message = "Cell phone number must adhere to the following format: XXX-XXXX-XXXX")
    private String cellPhone;

    @NotNull(message = "Home phone cannot be null.")
    @NotBlank(message = "Home phone is required.")
    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}", message = "Home phone number must adhere to the following format: XXX-XXXX-XXXX")
    private String homePhone;

}
