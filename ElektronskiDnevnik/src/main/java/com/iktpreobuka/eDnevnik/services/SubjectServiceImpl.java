package com.iktpreobuka.eDnevnik.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeachesEntity;
import com.iktpreobuka.eDnevnik.repositories.SubjectRepository;
import com.iktpreobuka.eDnevnik.repositories.TeachesRepository;

@Service
public class SubjectServiceImpl implements SubjectService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public SubjectRepository subjectRepository;
	
	@Autowired
	public TeachesRepository teachesRepository;
	
	public SubjectEntity createSubject(String name, int fundHours) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called create subject.",email);
		
		logger.info("Subject {} successfully created.", name);
		return subjectRepository.save(new SubjectEntity(name,fundHours,new ArrayList<TeachesEntity>()));
	}
	
	public boolean deleteSubject(String name) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called delete subject.",email);
		SubjectEntity retSub=subjectRepository.findByName(name);
		if(retSub == null) {
			logger.error("Subject {} could not be deleted, it does not exist.", name);
			return false;
		}
		
		for(TeachesEntity t : retSub.getTeaches()) {
			teachesRepository.delete(t);
		}
		
		subjectRepository.delete(retSub);
		logger.info("Subject {} successfully deleted.", name);
		return true;
	}
	
	public boolean updateSubject(String name, int fundHours) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called update subject.",email);
		SubjectEntity retSub= subjectRepository.findByName(name);
		if (retSub == null) {
			logger.error("Subject {} could not be updated, it does not exist.", name);
			return false;
		}
			
		if (fundHours >= 0) {
			retSub.setFundHours(fundHours);
		}
		
		subjectRepository.save(retSub);
		logger.info("Subject {} successfully updated.", name);
		return true;
	}
	
	public List<SubjectEntity> listSubjects() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called list all subjects.",email);
		
		return (List<SubjectEntity>)subjectRepository.findAll();
	}
}
