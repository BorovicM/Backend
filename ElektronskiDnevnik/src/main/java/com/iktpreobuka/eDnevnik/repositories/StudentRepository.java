package com.iktpreobuka.eDnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.StudentEntity;


public interface StudentRepository extends CrudRepository<StudentEntity,Integer>{

	StudentEntity findByEmail(String email);

}
