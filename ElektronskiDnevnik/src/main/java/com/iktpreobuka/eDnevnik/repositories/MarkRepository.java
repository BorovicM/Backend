package com.iktpreobuka.eDnevnik.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.MarkEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.TeachesEntity;

public interface MarkRepository extends CrudRepository<MarkEntity, Integer> {

	public List<MarkEntity> findByStudentAndEvaluatesAndMarkAndSemesterAndDate(StudentEntity student, TeachesEntity retTeaches,
			int mark, int semester, LocalDate date);
	

}
