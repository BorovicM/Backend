package com.iktpreobuka.eDnevnik.entities.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RequestMarkWithDateDto extends RequestMarkDto {
	
	@NotNull(message = "Date must not be null")
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
	private LocalDate date;

	public RequestMarkWithDateDto() {}
	
	public RequestMarkWithDateDto(String studentEmail, String subjectName, int mark, int semester, LocalDate date) {
		super(studentEmail, subjectName, mark, semester);
		this.date = date;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
