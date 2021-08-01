package com.iktpreobuka.eDnevnik.entities.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class GradeDto {

	@NotNull(message = "Grade must not be null")
	@Min(value = 0, message = "Grade number cannot be less than zero.")
//	@Pattern(regexp = "^[0-9]+$", message = "Grade can contain only numeric characters.")
	private int grade;
	
	@NotNull(message = "Grade mark must not be null")
	@Min(value = 0, message = "Grade mark number cannot be less than zero.")
//	@Pattern(regexp = "^[0-9]+$", message = "Grade mark can contain only numeric characters.")
	private int gradeMark;
	
	public GradeDto() {}
	
	public GradeDto(int grade, int gradeMark) {
		super();
		this.grade = grade;
		this.gradeMark = gradeMark;
	}
	
	public int getGrade() {
		return grade;
	}
	
	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	public int getGradeMark() {
		return gradeMark;
	}
	
	public void setGradeMark(int gradeMark) {
		this.gradeMark = gradeMark;
	}
}
