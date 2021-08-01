package com.iktpreobuka.eDnevnik.entities.dto;

public class ResponseStudentDto extends ResponseUserDto {

	private String parentName;
	private int grade;
	private int gradeMark;

	public ResponseStudentDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	


	public ResponseStudentDto(String name, String lastName, String email, String parentName, int grade, int gradeMark) {
		super(name, lastName, email);
		this.parentName = parentName;
		this.grade = grade;
		this.gradeMark = gradeMark;
	}





	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
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
