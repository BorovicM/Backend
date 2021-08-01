package com.iktpreobuka.eDnevnik.entities.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RequestSubjectDto {
	
	@NotNull(message = "Subject name must not be null")
	@Size(min = 1, max = 30, message = "Subject name must be between {min} and {max} characters.")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+$", message = "Subject name can contain only alphanumeric characters.")
	private String name;
	
	@NotNull(message = "Fund number must not be null")
	@Min(value = 0, message = "Fund number cannot be less than zero.")
//	@Pattern(regexp = "^[0-9]+$", message = "Fund number can contain only numeric.")
	private int fundNumber;
	
	public RequestSubjectDto() {}

	public RequestSubjectDto(String name, int fundNumber) {
		super();
		this.name = name;
		this.fundNumber = fundNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFundNumber() {
		return fundNumber;
	}

	public void setFundNumber(int fundNumber) {
		this.fundNumber = fundNumber;
	}
}
