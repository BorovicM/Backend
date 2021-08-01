package com.iktpreobuka.eDnevnik.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.MarkEntity;
import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.TeachesEntity;
import com.iktpreobuka.eDnevnik.entities.dto.GradeDto;
import com.iktpreobuka.eDnevnik.entities.dto.ResponseUserDto;
import com.iktpreobuka.eDnevnik.repositories.GradeRepository;
import com.iktpreobuka.eDnevnik.repositories.MarkRepository;
import com.iktpreobuka.eDnevnik.repositories.StudentRepository;
import com.iktpreobuka.eDnevnik.repositories.SubjectRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherRepository;
import com.iktpreobuka.eDnevnik.repositories.TeachesRepository;

@Service
public class TeachesServiceImpl implements TeachesService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public TeachesRepository teachesRepository;
	
	@Autowired
	public TeacherRepository teacherRepository;
	
	@Autowired
	public GradeRepository gradeRepository;
	
	@Autowired
	public SubjectRepository subjectRepository;
	
	@Autowired
	public StudentRepository studentRepository;
	
	@Autowired
	public MarkRepository markRepository;

	@Override
	public TeachesEntity createTeaches(int grade, int markGrade, String emailTeacher, String nameSubject) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called create teaches.",email);
		
		GradeEntity retGrade = gradeRepository.findByGradeAndMarkGrade(grade, markGrade);
		if(retGrade==null) {
			logger.error("Teaches could not be created, grade {}-{} does not exist", grade, markGrade);
			return null;
		}
		
		TeacherEntity retUser=teacherRepository.findByEmail(emailTeacher);
		if(retUser==null) {
			logger.error("Teaches could not be created, teacher {} does not exist", emailTeacher);
			return null;
		}
		
		SubjectEntity retSub=subjectRepository.findByName(nameSubject);
		if(retSub==null) {
			logger.error("Teaches could not be created, subject {} does not exist", nameSubject);
			return null;
		}
		
		TeachesEntity retTeaches = teachesRepository.save(new TeachesEntity(retGrade, retUser, retSub));
		teachesRepository.save(retTeaches);
		//retGrade.getTeachs().add(retTeaches);
		//gradeRepository.save(retGrade);
		//retUser.getTeaches().add(retTeaches);
		//teacherRepository.save(retUser);
		//retSub.getTeaches().add(retTeaches);
		//subjectRepository.save(retSub);
		
		logger.info("Teaches for teacher {} in grade {}-{} successfully created.", emailTeacher, grade, markGrade);
		return retTeaches;
	}

	@Override
	public boolean deleteTeaches(int grade, int markGrade, String emailTeacher, String nameSubject) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called delete teaches.",email);
		
		GradeEntity retGrade = gradeRepository.findByGradeAndMarkGrade(grade, markGrade);
		if(retGrade==null) {
			logger.error("Teaches could not be deleted, grade {}-{} does not exist", grade, markGrade);
			return false;
		}
		
		TeacherEntity retUser=teacherRepository.findByEmail(emailTeacher);
		if(retUser==null) {
			logger.error("Teaches could not be deleted, teacher {} does not exist", emailTeacher);
			return false;
		}
		
		SubjectEntity retSub=subjectRepository.findByName(nameSubject);
		if(retSub==null) {
			logger.error("Teaches could not be updated, subject {} does not exist", nameSubject);
			return false;
		}
		
		TeachesEntity retTeaches = teachesRepository.findByGradeAndTeacherAndSubject(retGrade, retUser, retSub);
		if (retTeaches == null) {
			logger.error("Teaches could not be deleted. Teacher {} does not teach in grade {}-{}.", emailTeacher, grade, markGrade);
			return false;
		}
		for(MarkEntity mark : retTeaches.getMarks()) {
			mark.setEvaluates(null);
			markRepository.save(mark);
		}
		teachesRepository.delete(retTeaches);
		logger.info("Teaches for teacher {} in grade {}-{} successfully deleted.", emailTeacher, grade, markGrade);
		return true;
	}

	@Override
	public List<TeachesEntity> listTeaches() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called get list of all teaches.",email);
		
		return (List<TeachesEntity>) teachesRepository.findAll();
	}
	
	@Override
	public List<SubjectEntity> listOfTeacherSubjects(String email) {
		String email1 = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called list of teacher subcjects.",email1);
		List<SubjectEntity> subjects = new ArrayList<SubjectEntity>();
		
		TeacherEntity teacher = teacherRepository.findByEmail(email);
		if (teacher == null) {
			logger.error("Teacher {} does not exist, subjects could not be retreived.", email);
			return subjects;
		}
		
		List<TeachesEntity> teaches = teachesRepository.findByTeacher(teacher);		
		
		for (TeachesEntity teach : teaches) {
			subjects.add(teach.getSubject());
		}
		
		logger.info("Get subjects for teacher {} called. Subject count: {}", email, subjects.size());
		return subjects;
	}
	
	@Override
	public List<GradeDto> listOfTeacherGrades(String email) {
		String email1 = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called get list of teacher grades.",email1);
		
		List<GradeDto> grades = new ArrayList<GradeDto>();
		
		TeacherEntity teacher = teacherRepository.findByEmail(email);
		if (teacher == null) {
			logger.error("Teacher {} does not exist, grades could not be retreived.", email);
			return grades;
		}
		
		List<TeachesEntity> teaches = teachesRepository.findByTeacher(teacher);		
		
		for (TeachesEntity teach : teaches) {
			grades.add(new GradeDto(teach.getGrade().getGrade(), teach.getGrade().getMark()));
		}
		
		logger.info("Get grades for teacher {} called. Grades count: {}", email, grades.size());
		return grades;
	}
	
	@Override
	public ResponseUserDto getTeacherOnSubjectinGrade(String subject, int grade, int gradeMark) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called get teacher on subject in grade.",email);
		
		GradeEntity retGrade = gradeRepository.findByGradeAndMarkGrade(grade, gradeMark);
		if(retGrade==null) {
			logger.error("Grade {}-{} does not exist, teacher could not be retreived.", grade, gradeMark);
			return null;
		}
		
		SubjectEntity retSub=subjectRepository.findByName(subject);
		if(retSub==null) {
			logger.error("Subject {} does not exist, teacher could not be retreived.", subject);
			return null;
		}
		
		TeachesEntity teaches = teachesRepository.findByGradeAndSubject(retGrade, retSub);
		if (teaches.getTeacher() == null) {
			logger.error("Teaches in grade {}-{} for subject {} does not exist, teacher could not be retreived.", grade, gradeMark, subject);
			return null;
		}
		
		logger.info("Teacher in grade {}-{} for subject {} is {}.", grade, gradeMark, subject, teaches.getTeacher().getName()+" "+teaches.getTeacher().getSurrname());
		return new ResponseUserDto(teaches.getTeacher().getName(), teaches.getTeacher().getSurrname(), teaches.getTeacher().getEmail());
	}
}
