package com.iktpreobuka.eDnevnik.entities.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RequestTeachesDto {
	
	@NotNull(message = "Grade must not be null")
	@Min(value = 0, message = "Grade number cannot be less than zero.")
//	@Pattern(regexp = "^[0-9]+$", message = "Grade can contain only numeric characters.")
	private int grade;
	
	@NotNull(message = "Grade mark must not be null")
	@Min(value = 0, message = "Grade mark number cannot be less than zero.")
//	@Pattern(regexp = "^[0-9]+$", message = "Grade mark can contain only numeric characters.")
	private int gradeMark;
	
	@NotNull(message = "Teacher email must not be null")
	@Size(min = 5, max = 30, message = "Teacher email must be between {min} and {max} characters.")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Teacher email can contain only alphanumeric characters.")
	private String teacherEmail;
	
	@NotNull(message = "Subject name must not be null")
	@Size(min = 1, max = 30, message = "Subject name must be between {min} and {max} characters.")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+$", message = "Subject name can contain only alphanumeric characters.")
	private String subjectName;

	public RequestTeachesDto() {}
	
	public RequestTeachesDto(int grade, int gradeMark, String teacherEmail, String subjectName) {
		super();
		this.grade = grade;
		this.gradeMark = gradeMark;
		this.teacherEmail = teacherEmail;
		this.subjectName = subjectName;
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

	public String getTeacherEmail() {
		return teacherEmail;
	}

	public void setTeacherEmail(String teacherEmail) {
		this.teacherEmail = teacherEmail;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	} 	
}
