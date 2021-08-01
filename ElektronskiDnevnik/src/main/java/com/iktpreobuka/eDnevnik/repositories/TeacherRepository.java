package com.iktpreobuka.eDnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.TeacherEntity;


public interface TeacherRepository extends CrudRepository<TeacherEntity,Integer>{

	TeacherEntity findByEmail(String email);

}
