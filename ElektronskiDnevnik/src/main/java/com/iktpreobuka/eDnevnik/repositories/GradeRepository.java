package com.iktpreobuka.eDnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.GradeEntity;

public interface GradeRepository extends CrudRepository<GradeEntity, Integer> {

	public GradeEntity findByGradeAndMarkGrade(int grade, int markGrade);
}
