package com.iktpreobuka.eDnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.TeachesEntity;

public interface TeachesRepository extends CrudRepository<TeachesEntity, Integer> {

	public TeachesEntity findByGradeAndTeacherAndSubject(GradeEntity retGrade, TeacherEntity retUser, SubjectEntity retSub);

	public List<TeachesEntity> findByTeacher(TeacherEntity teacher);

	public TeachesEntity findByGradeAndSubject(GradeEntity retGrade, SubjectEntity retSub);

}
