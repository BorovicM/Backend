package com.iktpreobuka.eDnevnik.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "student")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StudentEntity extends UserEntity {
	
	@ManyToOne (cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	@JoinColumn(name = "grade")
	@JsonManagedReference(value="SG")
	private GradeEntity grade;
	
	@ManyToOne (cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	@JoinColumn(name = "parent")
	@JsonManagedReference(value="PS")
	private ParentEntity parent;
	
	@OneToMany (mappedBy = "student", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonManagedReference (value="SM")
	private List<MarkEntity> mark;
		
	public StudentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentEntity(String name, String surrname, String email, String password, GradeEntity grade) {
		super(name, surrname, email, password, RoleEntity.ROLE_STUDENT);
		this.grade = grade;
		this.mark = new ArrayList<MarkEntity>();
	}
	
	public StudentEntity(String name, String surrname, String email, String password, GradeEntity grade,
			ParentEntity parent, List<MarkEntity> mark) {
		super(name, surrname, email, password, RoleEntity.ROLE_STUDENT);
		this.grade = grade;
		this.parent = parent;
		this.mark = mark;
	}

	public GradeEntity getGrade() {
		return grade;
	}

	public void setGrade(GradeEntity grade) {
		this.grade = grade;
	}

	public ParentEntity getParent() {
		return parent;
	}

	public void setParent(ParentEntity parent) {
		this.parent = parent;
	}

	public List<MarkEntity> getMark() {
		return mark;
	}

	public void setMark(List<MarkEntity> mark) {
		this.mark = mark;
	}
}
