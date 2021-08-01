package com.iktpreobuka.eDnevnik.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "teaches")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TeachesEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "grade")
	@JsonBackReference(value="Gsub")
	private GradeEntity grade;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher")
	@JsonBackReference(value="TH")
	private TeacherEntity teacher;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "subject")
	@JsonBackReference(value="Subt")
	private SubjectEntity subject;
	
	@OneToMany (mappedBy="evaluates",cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference (value="EV")
	private List<MarkEntity> marks;
	
	public TeachesEntity() {}
	
	public TeachesEntity(GradeEntity grade, TeacherEntity teacher, SubjectEntity subject) {
		super();
		this.grade = grade;		
		this.teacher = teacher;
		this.marks= new ArrayList<MarkEntity>();
		this.subject=subject;		
	}
	
	public TeachesEntity(GradeEntity grade, TeacherEntity teacher, List<MarkEntity> marks, SubjectEntity subject) {
		super();
		this.grade = grade;		
		this.teacher = teacher;
		this.marks= marks;
		this.subject=subject;		
	}

	public GradeEntity getGrade() {
		return grade;
	}

	public void setGrade(GradeEntity grade) {
		this.grade = grade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TeacherEntity getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherEntity teacher) {
		this.teacher = teacher;
	}

	public SubjectEntity getSubject() {
		return subject;
	}

	public void setSubject(SubjectEntity subject) {
		this.subject = subject;
	}

	public List<MarkEntity> getMarks() {
		return marks;
	}

	public void setMarks(List<MarkEntity> marks) {
		this.marks = marks;
	}
}
