package com.iktpreobuka.eDnevnik.entities.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RequestNewUserDto {

	@NotNull(message = "Username must not be null")
	@Size(min = 5, max = 30, message = "Username must be between {min} and {max} characters.")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Username can contain only alphanumeric characters and must be email.")
	private String email;
	
	@NotNull(message = "Password must not be null")
	@Size(min = 5, max = 15, message = "Password must be between {min} and {max} characters.")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Password can contain only alphanumeric characters.")
	private String password;
	
	@NotNull(message = "First name must not be null.")
	@Size(min = 2, max = 30, message = "First name must be between {min} and {max} characters.")
	@Pattern(regexp = "^[a-zA-Z]*$", message = "First name can contain only uppercase and lowercase letters.")
	private String name;
	
	@NotNull(message = "Last name must not be null.")
	@Size(min = 2, max = 30, message = "Last name must be between {min} and {max} characters.")
	@Pattern(regexp = "^[a-zA-Z]*$", message = "Last name can contain only uppercase and lowercase letters.")
	private String lastName;
	
	public RequestNewUserDto() {}

	public RequestNewUserDto(String email, String password, String name, String lastName) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
