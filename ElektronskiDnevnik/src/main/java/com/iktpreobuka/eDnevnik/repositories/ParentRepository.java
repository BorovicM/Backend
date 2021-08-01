package com.iktpreobuka.eDnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.ParentEntity;

public interface ParentRepository extends CrudRepository<ParentEntity,Integer>{

	ParentEntity findByEmail(String email);

}
