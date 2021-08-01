package com.iktpreobuka.eDnevnik.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "teacher")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TeacherEntity extends UserEntity {
		
	@OneToMany (mappedBy="teacher",cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	@JsonManagedReference (value="TH")
	private List<TeachesEntity> teaches;
		
	public TeacherEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeacherEntity( String name, String surrname, String email, String password) {
		super( name, surrname, email, password,RoleEntity.ROLE_TEACHER);
		
		this.teaches = new ArrayList<TeachesEntity>();
	}

	public TeacherEntity( String name, String surrname, String email, String password, List<TeachesEntity> teaches) {
		super( name, surrname, email, password,RoleEntity.ROLE_TEACHER);
		
		this.teaches = teaches;
	}

	public List<TeachesEntity> getTeaches() {
		return teaches;
	}

	public void setTeaches(List<TeachesEntity> teaches) {
		this.teaches = teaches;
	}
}
