package com.iktpreobuka.eDnevnik.services;

import java.util.List;

import com.iktpreobuka.eDnevnik.entities.ParentEntity;

public interface ParentService {

	public ParentEntity addParent(String name, String email, String lastName, String password);
	
	public boolean deleteParent(String email);
	
	public boolean updateParent(String name, String email, String lastName, String password);
	
	public List<ParentEntity> listUsers();
}
