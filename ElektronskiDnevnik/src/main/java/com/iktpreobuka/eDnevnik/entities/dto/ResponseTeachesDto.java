package com.iktpreobuka.eDnevnik.entities.dto;

public class ResponseTeachesDto {

	private int grade;

	private int gradeMark;

	private String subjectName;
	private String teacher;

	public ResponseTeachesDto() {
	}

	public ResponseTeachesDto(int grade, int gradeMark, String subjectName, String teacher) {
		super();
		this.grade = grade;
		this.gradeMark = gradeMark;
		this.subjectName = subjectName;
		this.teacher = teacher;
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

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	
}
