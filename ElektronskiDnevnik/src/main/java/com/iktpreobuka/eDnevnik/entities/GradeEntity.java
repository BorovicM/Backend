package com.iktpreobuka.eDnevnik.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="grade",
    uniqueConstraints={
      @UniqueConstraint(columnNames = {"grade", "markGrade"})
	}) 
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GradeEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column
	private int grade;
	
	@Column
	private int markGrade;
	
	@OneToMany (mappedBy="grade",cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	@JsonManagedReference (value="GSub")
	private List<TeachesEntity> teachs;
	
	@OneToMany (mappedBy="grade",cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	@JsonManagedReference (value="SG")
	private List<StudentEntity> students;
	
	public GradeEntity() {}
	
	public GradeEntity(int grade, int markGrade) {
		super();
		
		this.grade = grade;
		this.markGrade = markGrade;
		this.teachs = new ArrayList<TeachesEntity>();
		this.students = new ArrayList<StudentEntity>();
	}
	
	public GradeEntity(int grade, int markGrade, List<TeachesEntity> teachs, List<StudentEntity> students) {
		super();
		
		this.grade = grade;
		this.markGrade = markGrade;
		this.teachs = teachs;
		this.students = students;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getMark() {
		return markGrade;
	}

	public List<TeachesEntity> getTeachs() {
		return teachs;
	}

	public void setTeachs(List<TeachesEntity> teachs) {
		this.teachs = teachs;
	}

	public void setMark(int markGrade) {
		this.markGrade = markGrade;
	}

	public List<StudentEntity> getStudents() {
		return students;
	}

	public void setStudents(List<StudentEntity> students) {
		this.students = students;
	}
}
