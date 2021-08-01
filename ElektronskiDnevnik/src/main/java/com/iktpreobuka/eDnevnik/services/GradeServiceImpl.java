package com.iktpreobuka.eDnevnik.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.TeachesEntity;
import com.iktpreobuka.eDnevnik.repositories.GradeRepository;
import com.iktpreobuka.eDnevnik.repositories.StudentRepository;
import com.iktpreobuka.eDnevnik.repositories.TeachesRepository;

@Service
public class GradeServiceImpl implements GradeService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public GradeRepository gradeRepository;
	
	@Autowired
	public TeachesRepository teachesRepository;
	
	@Autowired
	public StudentRepository studentRepository;

	public GradeEntity createGrade(int grade, int markGrade) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called create grade.",email);
		
		logger.info("Grade {}-{} created.", grade, markGrade);
		return gradeRepository.save(new GradeEntity(grade, markGrade));
	}
	
	public boolean deleteGrade(int grade, int markGrade) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called delete grade.",email);
		
		GradeEntity retGrade = gradeRepository.findByGradeAndMarkGrade(grade, markGrade);
		if (retGrade == null) {
			logger.info("Grade {}-{} could not be deleted, it does not exist.", grade, markGrade);
			return false;
		}
		
		for (TeachesEntity t : retGrade.getTeachs()) {
			teachesRepository.delete(t);
		}
		
		for (StudentEntity st : retGrade.getStudents()) {
			st.setGrade(null);
			studentRepository.save(st);
		}
		
		gradeRepository.delete(retGrade);
		logger.info("Grade {}-{} successfully deleted.", grade, markGrade);
		return true;
	}

	public List<GradeEntity> listGrades() {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called get list of grades.",email);
		return (List<GradeEntity>) gradeRepository.findAll();
	}
}
