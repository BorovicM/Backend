package com.iktpreobuka.eDnevnik.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.Util.Encryption;
import com.iktpreobuka.eDnevnik.entities.ParentEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.repositories.ParentRepository;
import com.iktpreobuka.eDnevnik.repositories.StudentRepository;

@Service
public class ParentServiceImpl implements ParentService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ParentRepository  parentRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	public ParentEntity addParent(String name, String email, String lastName, String password) {
		
		String email1 = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called add parent.",email1);
			
		ParentEntity retUser = parentRepository.save(new ParentEntity(name, lastName, email, Encryption.getPassEncoded(password), null));
			
		logger.info("Parent {} successfully created", email);
		return retUser;
	}
	
	public boolean deleteParent(String email) {
		String email1 = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called delete parent",email1);
		ParentEntity retUser=parentRepository.findByEmail(email);		
		if(retUser == null) {
			logger.error("Parent {} could not be deleted, it does not exist", email);
			return false;
		}
		
		for (StudentEntity s : retUser.getKid()) {
			s.setParent(null);
			studentRepository.save(s);
		}
			
		parentRepository.delete(retUser);
		logger.info("Parent {} successfully deleted", email);
		return true;
	}
	
	public boolean updateParent(String name, String email, String lastName, String password) {
		String email1 = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called update parent.",email1);
		ParentEntity retUser= parentRepository.findByEmail(email);		
		if (retUser == null) {
			logger.error("Parent {} could not be updated, it does not exist", email);
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
			
		parentRepository.save(retUser);
		logger.info("Parent {} successfully updated", email);
		return true;
	}
	
	public List<ParentEntity> listUsers() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called get list off all parents.",email);
		
		return (List<ParentEntity>)parentRepository.findAll();
	}
}
