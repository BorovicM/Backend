package com.iktpreobuka.eDnevnik.services;

import java.util.List;

import javax.validation.Valid;

import com.iktpreobuka.eDnevnik.entities.MarkEntity;
import com.iktpreobuka.eDnevnik.entities.dto.RequestMarkDto;
import com.iktpreobuka.eDnevnik.entities.dto.RequestMarkWithDateDto;
import com.iktpreobuka.eDnevnik.entities.dto.RespnoseMarkWithGradeDto;
import com.iktpreobuka.eDnevnik.entities.dto.RequestUpdateMarkDto;
import com.iktpreobuka.eDnevnik.entities.dto.ResponseMarkDto;

public interface MarkService {
	
	public boolean createMark(RequestMarkDto mark);
	
	public boolean createMarkTeacher(RequestMarkDto mark, String teacherEmail);
	
	public boolean deleteMark(RequestMarkWithDateDto mark);
	
	public boolean updateMarkTeacher(RequestUpdateMarkDto mark, String teacherEmail);
	
	public List<MarkEntity> getMarks();
	
	public List<RespnoseMarkWithGradeDto> getMarksByTeacher(String teacherEmail);
	
	public List<ResponseMarkDto> getMarksForStudent(String studentEmail);
	
	public List<ResponseMarkDto> getMarksForGrade(int grade, int gradeMark);
	
	public List<ResponseMarkDto> getMarksForTeacherInGrade(String teacherEmail, int grade, int gradeMark);
	
	public List<ResponseMarkDto> getMarksForParent(String parentEmail);

	public List<ResponseMarkDto> getMarksForStudentOnSubject(String studentEmail, String subjectName);

	public boolean deleteMarkForTeacher(@Valid RequestMarkWithDateDto mark, String email);
}
