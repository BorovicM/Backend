package com.iktpreobuka.eDnevnik.entities;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AdminEntity extends UserEntity{

	public AdminEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AdminEntity( String name, String surrname, String email, String password ) {
		super( name, surrname, email, password,RoleEntity.ROLE_ADMIN);
		
	}	
}
