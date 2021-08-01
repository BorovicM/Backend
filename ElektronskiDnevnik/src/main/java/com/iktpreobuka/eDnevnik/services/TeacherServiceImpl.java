package com.iktpreobuka.eDnevnik.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.Util.Encryption;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.TeachesEntity;
import com.iktpreobuka.eDnevnik.repositories.TeacherRepository;
import com.iktpreobuka.eDnevnik.repositories.TeachesRepository;

@Service
public class TeacherServiceImpl implements TeacherService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public TeacherRepository teacherRepository;
	
	@Autowired
	public TeachesRepository teachesRepository;
	
	public TeacherEntity createTeacher(String name, String email, String lastName, String password ) {
		
		String email1 = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called create teacher.",email1);
		
		logger.info("Teacher {} successfully created.", email);
		TeacherEntity retUser = teacherRepository.save(new TeacherEntity(name,lastName,email,Encryption.getPassEncoded(password), new ArrayList<TeachesEntity>()));
		return retUser;
	}
	
	public boolean deleteTeacher(String email) {
		
		String email1 = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called delete teacher.",email1);
		
		TeacherEntity retUser = teacherRepository.findByEmail(email);
		if(retUser == null) {
			logger.error("Teached {} could not be deleted, it does not exist.", email);
			return false;
		}
		
		for (TeachesEntity t : retUser.getTeaches()) {
			teachesRepository.delete(t);
		}
	
		teacherRepository.delete(retUser);
		logger.info("Teacher {} successfully deleted.", email);
		return true;
	}
	
	public boolean updateTeacher(String name, String email, String lastName, String password) {
		
		String email1 = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called create markupdate teacher.",email1);
		
		TeacherEntity retUser= teacherRepository.findByEmail(email);
		if(retUser == null) {
			logger.error("Teached {} could not be updated, it does not exist.", email);
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
		
		teacherRepository.save(retUser);
		logger.info("Teacher {} successfully updated.", email);
		return true;
	}
	
	public List<TeacherEntity> listUsers() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called list all teachers.",email);
		
		return (List<TeacherEntity>)teacherRepository.findAll();
	}
}
