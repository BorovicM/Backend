package com.iktpreobuka.eDnevnik.services;

import java.util.List;

import com.iktpreobuka.eDnevnik.entities.AdminEntity;

public interface AdminService {
	
	public AdminEntity createAdmin(String name, String email, String lastName, String password);
	
	public boolean deleteAdmin(String email);
	
	public boolean updateAdmin(String name, String email, String lastName, String password);

	public List<AdminEntity> listUsers();
}
