package com.iktpreobuka.eDnevnik.services;

import java.util.List;

import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.dto.ResponseUserDto;

public interface StudentService {
	
	public StudentEntity createStudent(String name, String email, String lastName, String password);

	public boolean deleteStudent(String email);

	public boolean updateStudent(String name, String email, String lastName, String password);

	public boolean addStudentToGrade(String email, int grade, int markGrade);

	public List<StudentEntity> listUsers();
	
	public List<SubjectEntity> listOfStudentSubjects(String email);
	
	public ResponseUserDto getParent(String studentEmail);

	public boolean addParentToStudent(String email, String parentEmail);
}
