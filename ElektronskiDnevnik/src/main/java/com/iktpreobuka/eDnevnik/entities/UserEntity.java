package com.iktpreobuka.eDnevnik.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class UserEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column
	private String name;
	
	@Column
	private String surrname;
	
	@Column(unique=true)
	private String email;
	
	@Column
	private String password;
	
	@Column(columnDefinition = "ENUM('ROLE_ADMIN', 'ROLE_STUDENT', 'ROLE_PARENT' , 'ROLE_TEACHER')")
	@Enumerated(EnumType.STRING)
	private RoleEntity role;

	public UserEntity() {}
	
	public UserEntity( String name, String surrname, String email, String password , RoleEntity role) {
		super();
		
		this.name = name;
		this.surrname = surrname;
		this.email = email;
		this.password = password;
		this.role=role;
	}

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurrname() {
		return surrname;
	}

	public void setSurrname(String surrname) {
		this.surrname = surrname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "UserEntity [name=" + name + ", surrname=" + surrname + ", email=" + email + ", role=" + role + "]";
	}
}
