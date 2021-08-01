package com.iktpreobuka.eDnevnik.services;

import java.util.List;

import com.iktpreobuka.eDnevnik.entities.SubjectEntity;

public interface SubjectService {
	
	public SubjectEntity createSubject(String name, int fundHours);
	
	public boolean deleteSubject(String name);
	
	public boolean updateSubject(String name, int fundHours);
	
	public List<SubjectEntity> listSubjects();
}
