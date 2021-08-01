package com.iktpreobuka.eDnevnik.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.Util.Encryption;
import com.iktpreobuka.eDnevnik.entities.AdminEntity;
import com.iktpreobuka.eDnevnik.repositories.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public AdminRepository adminRepository;
	
	@Override
	public AdminEntity createAdmin(String name, String lastName, String email, String password) {
		
		String email1 = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called create admin.",email1);
		
		AdminEntity retUser = adminRepository.save(new AdminEntity(name, lastName, email, Encryption.getPassEncoded(password)));
		String callerEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		
		logger.info("User {} is adding new admin.", callerEmail);
		return retUser;
	}
	
	@Override
	public boolean deleteAdmin(String email) {
		
		String email1 = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called delete admin.",email1);
		
		AdminEntity retUser=adminRepository.findByEmail(email);
		if (retUser == null) {
			logger.error("Admin {} could not be deleted, it does not exist.", email);
			return false;
		}
		
		adminRepository.delete(retUser);
		logger.info("Admin {} succesfully deleted.", email);
		return true;		
	}
	
	@Override
	public boolean updateAdmin(String name, String email, String lastName, String password) {
		
		String email1 = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called update admin.",email1);
		
		AdminEntity retUser= adminRepository.findByEmail(email);
		if(retUser == null) {
			logger.error("Admin {} could not be updated, it does not exist.", email);
			return false; 
		}
		
		if(name !=null && name!="") {
			retUser.setName(name);
		}
			
		if(lastName!=null && lastName !="") {
			retUser.setSurrname(lastName);
		}
			
		if(password !=null && password!="") {
			retUser.setPassword(Encryption.getPassEncoded(password));
		}
			
		adminRepository.save(retUser);
		logger.info("Admin {} succesfully updated.", email);
		return true;
	}

	@Override
	public List<AdminEntity> listUsers() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called get list of admins.",email);
		
		return (List<AdminEntity>)adminRepository.findAll();
	}
}
