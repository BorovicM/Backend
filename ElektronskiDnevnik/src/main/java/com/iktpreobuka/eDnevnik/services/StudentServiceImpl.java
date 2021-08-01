package com.iktpreobuka.eDnevnik.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.Util.Encryption;
import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.MarkEntity;
import com.iktpreobuka.eDnevnik.entities.ParentEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeachesEntity;
import com.iktpreobuka.eDnevnik.entities.dto.ResponseUserDto;
import com.iktpreobuka.eDnevnik.repositories.GradeRepository;
import com.iktpreobuka.eDnevnik.repositories.MarkRepository;
import com.iktpreobuka.eDnevnik.repositories.ParentRepository;
import com.iktpreobuka.eDnevnik.repositories.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public StudentRepository studentRepository;
	
	@Autowired
	public GradeRepository gradeRepository;
	
	@Autowired
	public ParentRepository parentRepository;
	
	@Autowired
	public MarkRepository markRepository;

	@Override
	public StudentEntity createStudent(String name, String email, String lastName, String password) {
		
		String email1 = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called create student.",email1);
		

		StudentEntity retUser = studentRepository.save(
				new StudentEntity(name, lastName, email, Encryption.getPassEncoded(password), null, null, null));
		
		logger.info("Student {} successfully created.", email);
		return retUser;
	}

	@Override
	public boolean deleteStudent(String email) {
		
		String email1 = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called delete student.",email1);
		
		StudentEntity retUser = studentRepository.findByEmail(email);
		if (retUser == null) {
			logger.error("Student {} could not be deleted, it does not exist.", email);
			return false;
		}
		
		if (retUser.getParent() != null) {
			parentRepository.delete(retUser.getParent());
		}
		
		/*if (retUser.getGrade() != null) {
			retUser.getGrade().getStudents().remove(retUser);
			gradeRepository.save(retUser.getGrade());
		}*/
	
		for (MarkEntity m : retUser.getMark()) {
			markRepository.delete(m);
		}
		
		studentRepository.delete(retUser);
		logger.info("Student {} successfully deleted.", email);
		return true;
	}

	@Override
	public boolean updateStudent( String name, String email, String lastName, String password) {
		
		String email1 = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called update student.",email1);
		
		StudentEntity retUser = studentRepository.findByEmail(email);
		if (retUser == null) {
			logger.error("Student {} could not be updated, it does not exist.", email);
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
				
		studentRepository.save(retUser);
		logger.info("Student {} successfully updated.", email);
		return true;
	}

	@Override
	public List<StudentEntity> listUsers() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called get list of students.",email);
		return (List<StudentEntity>) studentRepository.findAll();
	}
	
	@Override
	public boolean addStudentToGrade(String email, int grade, int markGrade){
		
		String email1 = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called add student to grade.",email1);
		
		StudentEntity retUser = studentRepository.findByEmail(email);
		if (retUser==null) {
			logger.error("Student {} could not be added to grade, it does not exist.", email);
			return false;
		}
		
		GradeEntity grades = gradeRepository.findByGradeAndMarkGrade(grade, markGrade);
		if (grades == null) {
			logger.error("Student {} could not be added to grade {}-{}, grade does not exist.", email, grade, markGrade);
			return false;
		}
		
		retUser.setGrade(grades);
		studentRepository.save(retUser);
		
		//grades.getStudents().add(retUser);
		//gradeRepository.save(grades);
		logger.info("Student {} successfully added to grade {}-{}.", email, grade, markGrade);
		return true;
	}
	
	@Override
	public boolean addParentToStudent(String email, String parentEmail) {
		
		String email1 = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called add parent to student.",email);
		
		StudentEntity retUser = studentRepository.findByEmail(email1);
		if (retUser==null) {
			logger.error("Parent cound not be added to student {}, student does not exist.", email);
			return false;
		}
		
		ParentEntity parent = parentRepository.findByEmail(parentEmail);
		if (parent==null) {
			logger.error("Parent {} could not be added to student {}, parent does not exist.", parentEmail, email);
			return false;
		}
		
		retUser.setParent(parent);
		studentRepository.save(retUser);
		
		logger.info("Parent {} successfully added to student {}.", parentEmail, email);
		return true;
	}
	
	@Override
	public List<SubjectEntity> listOfStudentSubjects(String email) {
		
		String email1 = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called list of student subjects.",email1);
		
		List<SubjectEntity> subjects = new ArrayList<SubjectEntity>();
		
		StudentEntity student = studentRepository.findByEmail(email);
		if (student == null || student.getGrade() == null) {
			logger.error("Student {} does not exist, subjects could not be retreived.", email);
			return subjects;
		}
		
		for (TeachesEntity teaches : student.getGrade().getTeachs()) {
			subjects.add(teaches.getSubject());
		}
		
		logger.info("Get subjects for student {} called. Subject count: {}", email, subjects.size());
		return subjects;
	}
	
	@Override
	public ResponseUserDto getParent(String studentEmail) {
		StudentEntity student = studentRepository.findByEmail(studentEmail);
		if (student == null || student.getParent() == null) {
			logger.error("Student {} does not exist, parent could not be retreived.", studentEmail);
			return null;
		}
		
		logger.info("Parent of student {} is {}", studentEmail, student.getParent().getName()+" "+ student.getParent().getSurrname());
		return new ResponseUserDto(student.getParent().getName(), student.getParent().getSurrname(),
				student.getParent().getEmail());
	}	
}
