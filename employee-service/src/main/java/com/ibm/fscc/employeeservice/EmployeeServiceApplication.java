package com.ibm.fscc.employeeservice;

import com.ibm.fscc.employeeservice.dto.EmployeeDTO;
import com.ibm.fscc.employeeservice.exception.EmployeeAlreadyExistsException;
import com.ibm.fscc.employeeservice.service.EmployeeService;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * The main class for starting the Employee Service application.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class EmployeeServiceApplication implements CommandLineRunner{

	private final EmployeeService employeeService;

	/**
	 * Constructs a new EmployeeServiceApplication with the specified EmployeeService.
	 *
	 * @param employeeService the EmployeeService to be used
	 */
	public EmployeeServiceApplication(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
	 * The entry point of the Employee Service application.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceApplication.class, args);
	}

	/**
	 * Runs the Employee Service application.
	 *
	 * @param args the command line arguments
	 */
	@Override
	public void run(String... args) {
		for (int i = 0; i < 25; i++) {
			String firstName = generateRandomName().split(" ")[0];
			String lastName = generateRandomName().split(" ")[1];
			createEmployee(
					generateRandomEmail(firstName, lastName),
					firstName,
					lastName,
					generateRandomAddress(),
					generateRandomState(),
					generateRandomZip(),
					generateRandomPhoneNumber(),
					generateRandomPhoneNumber()
			);
		}
	}

	/**
	 * Creates an employee with the given information.
	 *
	 * @param firstName  the first name of the employee
	 * @param lastName   the last name of the employee
	 * @param address    the address of the employee
	 * @param state      the state of the employee
	 * @param zip        the zip code of the employee
	 * @param cellPhone  the cell phone number of the employee
	 * @param homePhone  the home phone number of the employee
	 * @param email      the email address of the employee
	 */
	private void createEmployee(String email, String firstName, String lastName, String address, String state, String zip, String cellPhone, String homePhone) {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEmail(email);
		employeeDTO.setFirstName(firstName);
		employeeDTO.setLastName(lastName);
		employeeDTO.setAddress(address);
		employeeDTO.setState(state);
		employeeDTO.setZip(zip);
		employeeDTO.setCellPhone(cellPhone);
		employeeDTO.setHomePhone(homePhone);

		try {
			employeeService.createEmployee(employeeDTO);
		} catch (EmployeeAlreadyExistsException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Generates a random first and last name from an array of names
	 * @return fullName
	 */
	private String generateRandomName() {
		String[] firstNames = { "John", "Jane", "Michael", "Emily", "David", "Sarah", "Robert", "Emma", "William", "Olivia" };
		String[] lastNames = { "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Taylor", "Clark" };

		String firstName = firstNames[new Random().nextInt(firstNames.length)];
		String lastName = lastNames[new Random().nextInt(lastNames.length)];

		return firstName + " " + lastName;
	}

	/**
	 * Generates a random address from an array of streets names 
	 * and an address number o fjust 123
	 * @return address
	 */
	private String generateRandomAddress() {
		String[] streets = { "Main Street", "Maple Avenue", "Oak Street", "Elm Road", "Cedar Lane", "Pine Drive", "Birch Court" };
		String street = streets[new Random().nextInt(streets.length)];
		String numericPrefix = "123";
		String address = numericPrefix + " " + street;

		if (address.length() > 50)
			address = address.substring(0, 50);

		return address;
	}
	
	/**
	 * Generates a random state suffix from an array of states
	 * @return state suffix
	 */
	private String generateRandomState() {
		String[] states = { "NY", "CA", "LA", "TX", "FL", "CO", "NC" };
		return states[new Random().nextInt(states.length)];
	}

	/**
	 * Generates just a zip code of 71280
	 * @return zipCode
	 */
	private String generateRandomZip() {
		return "71280";
	}

	/**
	 * Generates a rando phone number 
	 * @return phoneNumber
	 */
	private String generateRandomPhoneNumber() {
		int areaCode = 200 + new Random().nextInt(800);
		int exchangeCode = new Random().nextInt(1000);
		int lineNumber = 1000 + new Random().nextInt(9000);

		return String.format("%03d-%03d-%04d", areaCode, exchangeCode, lineNumber);
	}

	/**
	 * Uses the first and last name passed to generate a random email
	 * 
	 * @param firstName
	 * @param lastName
	 * @return email
	 */
	private String generateRandomEmail(String firstName, String lastName) {
		String[] domains = { "ibm.com", "us.ibm.com", "gmail.com" };
		String randomDomain = domains[new Random().nextInt(domains.length)];

		// Convert the first name and last name to lowercase for the email
		String emailFirstName = firstName.toLowerCase();
		String emailLastName = lastName.toLowerCase();

		return emailFirstName + "." + emailLastName + "@" + randomDomain;
	}
}
