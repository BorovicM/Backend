package com.iktpreobuka.eDnevnik.services;

import java.util.List;

import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeachesEntity;
import com.iktpreobuka.eDnevnik.entities.dto.GradeDto;
import com.iktpreobuka.eDnevnik.entities.dto.ResponseUserDto;

public interface TeachesService {

	public TeachesEntity createTeaches(int grade, int markGrade, String emailTeacher, String nameSubject);

	public boolean deleteTeaches(int grade, int markGrade, String emailTeacher, String nameSubject);

	public List<TeachesEntity> listTeaches();
	
	public List<SubjectEntity> listOfTeacherSubjects(String email);
	
	public List<GradeDto> listOfTeacherGrades(String email);
	
	public ResponseUserDto getTeacherOnSubjectinGrade(String subject, int grade, int gradeMark);
}
