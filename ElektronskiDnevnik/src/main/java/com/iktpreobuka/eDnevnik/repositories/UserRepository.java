package com.iktpreobuka.eDnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.eDnevnik.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity,Integer> {

	UserEntity findByEmail(String email);

}
