package com.iktpreobuka.eDnevnik.services;

import java.util.List;

import com.iktpreobuka.eDnevnik.entities.UserEntity;
import com.iktpreobuka.eDnevnik.entities.dto.ResponseUserTokenDto;

public interface UserService {
	
	public boolean emailExists(String email);
	
	public ResponseUserTokenDto login(String email, String password);
	
	public List<UserEntity> getUsers();

}
