package com.iktpreobuka.eDnevnik.entities.dto;

public class ResponseUserDto {

	private String name;
	
	private String lastName;
	
	private String email;
	
	public ResponseUserDto() {}

	public ResponseUserDto(String name, String lastName, String email) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.email = email;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
