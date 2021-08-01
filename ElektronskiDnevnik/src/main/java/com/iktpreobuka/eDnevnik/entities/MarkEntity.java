package com.iktpreobuka.eDnevnik.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "mark")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MarkEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column
	private LocalDate date;
	
	@Column
	private int semester;
	
	@Column
	private int mark;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "student")
	@JsonBackReference(value="SM")
	private StudentEntity student;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "evaluates")
	@JsonBackReference(value="EV")
	private TeachesEntity evaluates;
	
	public MarkEntity() {}
	
	public MarkEntity(StudentEntity student, TeachesEntity evaluates, int mark, int semester, LocalDate date) {
		super();
		this.mark = mark;		
		this.date = date;
		this.semester = semester;
		this.student = student;
		this.evaluates = evaluates;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public StudentEntity getStudent() {
		return student;
	}

	public void setStudent(StudentEntity student) {
		this.student = student;
	}

	public TeachesEntity getEvaluates() {
		return evaluates;
	}

	public void setEvaluates(TeachesEntity evaluates) {
		this.evaluates = evaluates;
	}
}
