package com.iktpreobuka.eDnevnik.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "parent")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ParentEntity extends UserEntity{

	@OneToMany (mappedBy="parent",cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference (value="PS")
	private List<StudentEntity> kid;

	public ParentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ParentEntity( String name, String surrname, String email, String password, List<StudentEntity> kid) {
		super( name, surrname, email, password,RoleEntity.ROLE_PARENT);
		this.kid = kid;
	}

	public List<StudentEntity> getKid() {
		return kid;
	}

	public void setKid(List<StudentEntity> kid) {
		this.kid = kid;
	}
}
