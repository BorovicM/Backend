package com.iktpreobuka.eDnevnik.entities.dto;

import java.time.LocalDate;

public class ResponseMarkDto {
	
	private String studentName;
	
	private String subject;
	
	private int semestar;
	
	private int mark;
	
	private LocalDate date;
	
	public ResponseMarkDto() {}
	
	public ResponseMarkDto(String name, String subject, int semestar, int mark, LocalDate date) {
		super();
		this.studentName = name;
		this.subject = subject;
		this.semestar = semestar;
		this.mark = mark;
		this.date = date;
	}	
	
	public String getName() {
		return studentName;
	}

	public void setName(String name) {
		this.studentName = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getSemestar() {
		return semestar;
	}

	public void setSemestar(int semestar) {
		this.semestar = semestar;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}	
}
