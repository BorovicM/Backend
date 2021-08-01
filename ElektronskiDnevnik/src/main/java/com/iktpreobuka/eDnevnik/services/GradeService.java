package com.iktpreobuka.eDnevnik.services;

import java.util.List;

import com.iktpreobuka.eDnevnik.entities.GradeEntity;

public interface GradeService {
	
	public GradeEntity createGrade(int grade, int markGrade);
	
	public boolean deleteGrade(int grade, int markGrade);

	public List<GradeEntity> listGrades();
}
