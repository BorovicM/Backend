package com.iktpreobuka.eDnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.SubjectEntity;

public interface SubjectRepository extends CrudRepository<SubjectEntity, Integer> {

	public SubjectEntity findByName(String name);

}
