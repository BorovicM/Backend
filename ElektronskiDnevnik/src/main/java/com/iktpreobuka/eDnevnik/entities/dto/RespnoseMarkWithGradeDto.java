package com.iktpreobuka.eDnevnik.entities.dto;

import java.time.LocalDate;

public class RespnoseMarkWithGradeDto extends ResponseMarkDto {
	
	private int grade;
	
	private int gradeMark;

	public RespnoseMarkWithGradeDto() {
		super();
	}

	public RespnoseMarkWithGradeDto(String name, String subject, int semestar, int mark, LocalDate date, int grade, int gradeMark) {
		super(name, subject, semestar, mark, date);
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
