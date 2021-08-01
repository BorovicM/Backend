package com.iktpreobuka.eDnevnik.entities.dto;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class RequestUpdateMarkDto extends RequestMarkWithDateDto {
	
	@NotNull(message = "Mark must not be null")
	@Min(value = 0, message = "Mark number cannot be less than zero.")
	@Max(value = 5, message = "Mark number cannot be greater than {value}.")
//	@Pattern(regexp = "^[0-9]+$", message = "Mark can contain only numeric characters.")
	private int newMark;
	
	public RequestUpdateMarkDto() {
		super();
	}

	public RequestUpdateMarkDto(String studentEmail, String subjectName, int mark, int semester,
			int newMark, LocalDate date) {
		super(studentEmail, subjectName, mark, semester, date);
		this.newMark = newMark;
	}

	public int getNewMark() {
		return newMark;
	}

	public void setNewMark(int newMark) {
		this.newMark = newMark;
	}
}
