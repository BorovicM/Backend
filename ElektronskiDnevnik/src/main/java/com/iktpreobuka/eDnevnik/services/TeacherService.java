package com.iktpreobuka.eDnevnik.services;

import java.util.List;

import com.iktpreobuka.eDnevnik.entities.TeacherEntity;

public interface TeacherService {

	public TeacherEntity createTeacher(String name, String email, String lastName, String password );
	
	public boolean deleteTeacher(String email);
	
	public boolean updateTeacher(String name, String email, String lastName, String password);
	
	public List<TeacherEntity> listUsers();
}
