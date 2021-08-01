package com.iktpreobuka.eDnevnik.entities.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RequestMarkDto {

	@NotNull(message = "Student email must not be null")
	@Size(min = 5, max = 30, message = "Student email must be between {min} and {max} characters.")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Student email can contain only alphanumeric characters.")
	private String studentEmail;
	
	@NotNull(message = "Subject name must not be null")
	@Size(min = 1, max = 30, message = "Subject name must be between {min} and {max} characters.")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+$", message = "Subject name can contain only alphanumeric characters.")
	private String subjectName; 
	
	@NotNull(message = "Mark must not be null")
	@Min(value = 0, message = "Mark number cannot be less than zero.")
	@Max(value = 5, message = "Mark number cannot be greater than {value}.")
//	@Pattern(regexp = "^[0-9]+$", message = "Mark can contain only numeric characters.")
	private int mark;
	
	@NotNull(message = "Semester must not be null")
	@Min(value = 0, message = "Semester number cannot be less than zero.")
	@Max(value = 2, message = "Semester number cannot be greater than {value}.")
//	@Pattern(regexp = "^[0-9]+$", message = "Semester can contain only numeric characters.")
	private int semester;
	
	public RequestMarkDto() {}
	
	public RequestMarkDto(String studentEmail, String subjectName, int mark, int semester) {
		super();
		this.studentEmail = studentEmail;
		this.subjectName = subjectName;
		this.mark = mark;
		this.semester = semester;
	}

	public String getStudentEmail() {
		return studentEmail;
	}
	
	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}
	
	public String getSubjectName() {
		return subjectName;
	}
	
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public int getMark() {
		return mark;
	}
	
	public void setMark(int mark) {
		this.mark = mark;
	}
	
	public int getSemester() {
		return semester;
	}
	
	public void setSemester(int semester) {
		this.semester = semester;
	}
}
