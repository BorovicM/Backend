package com.iktpreobuka.eDnevnik.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.Util.EmailObject;
import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.MarkEntity;
import com.iktpreobuka.eDnevnik.entities.ParentEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.TeachesEntity;
import com.iktpreobuka.eDnevnik.entities.dto.RequestMarkDto;
import com.iktpreobuka.eDnevnik.entities.dto.RequestMarkWithDateDto;
import com.iktpreobuka.eDnevnik.entities.dto.RespnoseMarkWithGradeDto;
import com.iktpreobuka.eDnevnik.entities.dto.RequestUpdateMarkDto;
import com.iktpreobuka.eDnevnik.entities.dto.ResponseMarkDto;
import com.iktpreobuka.eDnevnik.repositories.GradeRepository;
import com.iktpreobuka.eDnevnik.repositories.MarkRepository;
import com.iktpreobuka.eDnevnik.repositories.ParentRepository;
import com.iktpreobuka.eDnevnik.repositories.StudentRepository;
import com.iktpreobuka.eDnevnik.repositories.SubjectRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherRepository;
import com.iktpreobuka.eDnevnik.repositories.TeachesRepository;

@Service
public class MarkServiceImpl implements MarkService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EmailService emailService;

	@Autowired
	private MarkRepository markRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	public TeachesRepository teachesRepository;

	@Autowired
	public GradeRepository gradeRepository;

	@Autowired
	public TeacherRepository teacherRepository;

	@Autowired
	public SubjectRepository subjectRepository;

	@Autowired
	public ParentRepository parentRepository;

	@Override
	public boolean createMark(RequestMarkDto mark) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called create mark.",email);
		
		StudentEntity student = studentRepository.findByEmail(mark.getStudentEmail());
		if (student == null || student.getGrade() == null) {
			logger.error("Mark could not be created, student {} does not exist.", mark.getStudentEmail());
			return false;
		}

		SubjectEntity retSub = subjectRepository.findByName(mark.getSubjectName());
		if (retSub == null) {
			logger.error("Mark could not be created, subject {} does not exist.", mark.getSubjectName());
			return false;
		}

		TeachesEntity retTeaches = teachesRepository.findByGradeAndSubject(student.getGrade(), retSub);
		if (retTeaches == null) {
			logger.error("Mark could not be created, student {} does not have subject {}.", mark.getStudentEmail(),
					mark.getSubjectName());
			return false;
		}

		markRepository.save(new MarkEntity(student, retTeaches, mark.getMark(), mark.getSemester(), LocalDate.now()));
		// proveriti da li je potrebno dodati u listu kod teaches

		logger.info("Mark {} for student {} in subject {} created.", mark.getMark(), mark.getStudentEmail(),
				mark.getSubjectName());
		return true;
	}

	@Override
	public boolean createMarkTeacher(RequestMarkDto mark, String teacherEmail) {
		
		logger.info("User {} called create mark for teacher.",teacherEmail);
		
		StudentEntity student = studentRepository.findByEmail(mark.getStudentEmail());
		if (student == null || student.getGrade() == null) {
			logger.error("Mark could not be created, student {} does not exist.", mark.getStudentEmail());
			return false;
		}

		SubjectEntity retSub = subjectRepository.findByName(mark.getSubjectName());
		if (retSub == null) {
			logger.error("Mark could not be created, subject {} does not exist.", mark.getSubjectName());
			return false;
		}

		TeachesEntity retTeaches = teachesRepository.findByGradeAndSubject(student.getGrade(), retSub);
		if (retTeaches == null) {
			logger.error("Mark could not be created, student {} does not have subject {}.", mark.getStudentEmail(),
					mark.getSubjectName());
			return false;
		}

		if (!retTeaches.getTeacher().getEmail().equals(teacherEmail)) {
			logger.error("Mark could not be created, {} is not teacher for student {}.", teacherEmail,
					mark.getStudentEmail());
			return false;
		}

		markRepository.save(new MarkEntity(student, retTeaches, mark.getMark(), mark.getSemester(), LocalDate.now()));

		if (student.getParent() != null) {
			String subject = "[EDnevnik] Dodata ocena iz predmeta " + retSub.getName();
			String text = "Postovani, obavestavamo Vas da je nastavnik " + retTeaches.getTeacher().getName() + " "
					+ retTeaches.getTeacher().getSurrname() + " dodao nova ocena " + mark.getMark() + " iz predmeta "
					+ retSub.getName() + " za ucenika " + student.getName() + " " + student.getSurrname() + ". "
					+ " Srdacan pozdrav, EDnevnik";
			EmailObject email = new EmailObject(student.getParent().getEmail(), subject, text);
			emailService.sendSimpleMessage(email);
		}

		logger.info("Mark {} for student {} in subject {} created.", mark.getMark(), mark.getStudentEmail(),
				mark.getSubjectName());
		return true;
	}

	@Override
	public boolean deleteMark(RequestMarkWithDateDto mark) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called delete mark.",email);
		
		StudentEntity student = studentRepository.findByEmail(mark.getStudentEmail());
		if (student == null || student.getGrade() == null) {
			logger.error("Mark could not be deleted, student {} does not exist.", mark.getStudentEmail());
			return false;
		}

		SubjectEntity retSub = subjectRepository.findByName(mark.getSubjectName());
		if (retSub == null) {
			logger.error("Mark could not be deleted, subject {} does not exist.", mark.getSubjectName());
			return false;
		}

		TeachesEntity retTeaches = teachesRepository.findByGradeAndSubject(student.getGrade(), retSub);
		if (retTeaches == null) {
			logger.error("Mark could not be deleted, student {} does not have subject {}.", mark.getStudentEmail(),
					mark.getSubjectName());
			return false;
		}

		List<MarkEntity> retMark = markRepository.findByStudentAndEvaluatesAndMarkAndSemesterAndDate(student,
				retTeaches, mark.getMark(), mark.getSemester(), mark.getDate());
		if (retMark == null || retMark.isEmpty()) {
			logger.error("Mark {} for student {} in subject {} not found and could not be deleted.", mark.getMark(),
					mark.getStudentEmail(), mark.getSubjectName());
			return false;
		}

		markRepository.delete(retMark.get(0));
		logger.info("Mark {} for student {} in subject {} deleted.", mark.getMark(), mark.getStudentEmail(),
				mark.getSubjectName());
		return true;
	}

	@Override
	public boolean updateMarkTeacher(RequestUpdateMarkDto mark, String teacherEmail) {
		
		logger.info("User {} called update mark by teacher.",teacherEmail);
		
		StudentEntity student = studentRepository.findByEmail(mark.getStudentEmail());
		if (student == null || student.getGrade() == null) {
			logger.error("Mark could not be updated, student {} does not exist.", mark.getStudentEmail());
			return false;
		}

		SubjectEntity retSub = subjectRepository.findByName(mark.getSubjectName());
		if (retSub == null) {
			logger.error("Mark could not be updated, subject {} does not exist.", mark.getSubjectName());
			return false;
		}

		TeachesEntity retTeaches = teachesRepository.findByGradeAndSubject(student.getGrade(), retSub);
		if (retTeaches == null) {
			logger.error("Mark could not be updated, student {} does not have subject {}.", mark.getStudentEmail(),
					mark.getSubjectName());
			return false;
		}

		if (!retTeaches.getTeacher().getEmail().equals(teacherEmail)) {
			logger.error("Mark could not be updated, {} is not teacher for student {}.", teacherEmail,
					mark.getStudentEmail());
			return false;
		}

		List<MarkEntity> retMark = markRepository.findByStudentAndEvaluatesAndMarkAndSemesterAndDate(student,
				retTeaches, mark.getMark(), mark.getSemester(), mark.getDate());
		if (retMark == null || retMark.isEmpty()) {
			logger.error("Mark {} for student {} in subject {} not found and could not be updated.", mark.getMark(),
					mark.getStudentEmail(), mark.getSubjectName());
			return false;
		}
		MarkEntity updateMark = retMark.get(0);
		updateMark.setMark(mark.getNewMark());
		markRepository.save(updateMark);
		logger.info("Mark {} for student {} in subject {} updated. New value: {}.", mark.getMark(),
				mark.getStudentEmail(), mark.getSubjectName(), mark.getNewMark());
		return true;
	}

	@Override
	public List<MarkEntity> getMarks() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called get marks.",email);
		
		logger.info("Get all marks called.");
		return (List<MarkEntity>) markRepository.findAll();
	}

	@Override
	public List<RespnoseMarkWithGradeDto> getMarksByTeacher(String teacherEmail) {
		
		logger.info("User {} called get marks by teacher.",teacherEmail);
		List<RespnoseMarkWithGradeDto> retMarks = new ArrayList<RespnoseMarkWithGradeDto>();

		TeacherEntity retTeacher = teacherRepository.findByEmail(teacherEmail);
		if (retTeacher == null) {
			logger.error("Teacher {} does not exist, marks could not be retreived.", teacherEmail);
			return retMarks;
		}

		for (TeachesEntity teaches : retTeacher.getTeaches()) {
			for (MarkEntity mark : teaches.getMarks()) {
				String name = mark.getStudent().getName() + " " + mark.getStudent().getSurrname();
				retMarks.add(new RespnoseMarkWithGradeDto(name, mark.getEvaluates().getSubject().getName(),
						mark.getSemester(), mark.getMark(), mark.getDate(), teaches.getGrade().getGrade(),
						teaches.getGrade().getMark()));
			}
		}

		logger.info("Get marks for teacher {} called. Marks count: {}", teacherEmail, retMarks.size());
		return retMarks;
	}

	@Override
	public List<ResponseMarkDto> getMarksForStudent(String studentEmail) {
		
		logger.info("User {} called get mars for student.",studentEmail);
		List<ResponseMarkDto> retMarks = new ArrayList<ResponseMarkDto>();

		StudentEntity student = studentRepository.findByEmail(studentEmail);
		if (student == null) {
			logger.error("Student {} does not exist, marks could not be retreived.", studentEmail);
			return retMarks;
		}

		for (MarkEntity mark : student.getMark()) {
			String name = student.getName() + " " + student.getSurrname();
			retMarks.add(new ResponseMarkDto(name, mark.getEvaluates().getSubject().getName(), mark.getSemester(),
					mark.getMark(), mark.getDate()));
		}

		logger.info("Get marks for student {} called. Marks count: {}", studentEmail, retMarks.size());
		return retMarks;
	}

	@Override
	public List<ResponseMarkDto> getMarksForParent(String parentEmail) {
		
		logger.info("User {} called marks for parent.",parentEmail);
		List<ResponseMarkDto> retMarks = new ArrayList<ResponseMarkDto>();

		ParentEntity parent = parentRepository.findByEmail(parentEmail);
		if (parent == null) {
			logger.error("Parent {} does not exist, marks could not be retreived.", parentEmail);
			return retMarks;
		}

		for (StudentEntity student : parent.getKid()) {
			for (MarkEntity mark : student.getMark()) {
				String name = student.getName() + " " + student.getSurrname();
				retMarks.add(new ResponseMarkDto(name, mark.getEvaluates().getSubject().getName(), mark.getSemester(),
						mark.getMark(), mark.getDate()));
			}
		}

		logger.info("Get marks for parent {} called. Marks count: {}", parentEmail, retMarks.size());
		return retMarks;
	}

	@Override
	public List<ResponseMarkDto> getMarksForGrade(int grade, int gradeMark) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User {} called get marks for grade.",email);
		List<ResponseMarkDto> retMarks = new ArrayList<ResponseMarkDto>();

		GradeEntity gradeEntity = gradeRepository.findByGradeAndMarkGrade(gradeMark, grade);
		if (gradeEntity == null) {
			logger.error("Grade {}-{} does not exist, marks could not be retreived.", grade, gradeMark);
			return retMarks;
		}

		for (StudentEntity student : gradeEntity.getStudents()) {
			String name = student.getName() + " " + student.getSurrname();
			for (MarkEntity mark : student.getMark()) {
				retMarks.add(new ResponseMarkDto(name, mark.getEvaluates().getSubject().getName(), mark.getSemester(),
						mark.getMark(), mark.getDate()));
			}
		}

		logger.info("Get marks for grade {}-{} called. Marks count: {}", grade, gradeMark, retMarks.size());
		return retMarks;
	}

	@Override
	public List<ResponseMarkDto> getMarksForTeacherInGrade(String teacherEmail, int grade, int gradeMark) {
		
		logger.info("User {} called get marks for teacher in grade.",teacherEmail);
		List<ResponseMarkDto> retMarks = new ArrayList<ResponseMarkDto>();

		GradeEntity gradeEntity = gradeRepository.findByGradeAndMarkGrade(gradeMark, grade);
		if (gradeEntity == null) {
			logger.error("Grade {}-{} does not exist, marks could not be retreived.", grade, gradeMark);
			return retMarks;
		}

		for (StudentEntity student : gradeEntity.getStudents()) {
			String name = student.getName() + " " + student.getSurrname();
			for (MarkEntity mark : student.getMark()) {
				if (mark.getEvaluates().getTeacher().getEmail().equals(teacherEmail)) {
					retMarks.add(new ResponseMarkDto(name, mark.getEvaluates().getSubject().getName(),
							mark.getSemester(), mark.getMark(), mark.getDate()));
				}
			}
		}

		logger.info("Get marks for teacher {} in grade {}-{} called. Marks count: {}", teacherEmail, grade, gradeMark,
				retMarks.size());
		return retMarks;
	}

	@Override
	public List<ResponseMarkDto> getMarksForStudentOnSubject(String studentEmail, String subjectName) {
		
		logger.info("User {} called create mark.",studentEmail);
		
		List<ResponseMarkDto> retMarks = new ArrayList<ResponseMarkDto>();

		StudentEntity student = studentRepository.findByEmail(studentEmail);
		if (student == null) {
			logger.error("Student {} does not exist, marks could not be retreived.", studentEmail);
			return retMarks;
		}

		for (MarkEntity mark : student.getMark()) {
			if (mark.getEvaluates().getSubject().getName().equals(subjectName)) {
				String name = student.getName() + " " + student.getSurrname();
				retMarks.add(new ResponseMarkDto(name, mark.getEvaluates().getSubject().getName(), mark.getSemester(),
						mark.getMark(), mark.getDate()));
			}
		}

		logger.info("Get marks for student {} on subject {} called. Marks count: {}", studentEmail, subjectName,
				retMarks.size());
		return retMarks;
	}

	public boolean deleteMarkForTeacher(@Valid RequestMarkWithDateDto mark, String teacherEmail) {
		
		logger.info("User {} called delete mark for teacher.",teacherEmail);
		
		StudentEntity student = studentRepository.findByEmail(mark.getStudentEmail());
		if (student == null || student.getGrade() == null) {
			logger.error("Mark could not be updated, student {} does not exist.", mark.getStudentEmail());
			return false;
		}

		SubjectEntity retSub = subjectRepository.findByName(mark.getSubjectName());
		if (retSub == null) {
			logger.error("Mark could not be updated, subject {} does not exist.", mark.getSubjectName());
			return false;
		}

		TeachesEntity retTeaches = teachesRepository.findByGradeAndSubject(student.getGrade(), retSub);
		if (retTeaches == null) {
			logger.error("Mark could not be updated, student {} does not have subject {}.", mark.getStudentEmail(),
					mark.getSubjectName());
			return false;
		}

		if (!retTeaches.getTeacher().getEmail().equals(teacherEmail)) {
			logger.error("Mark could not be updated, {} is not teacher for student {}.", teacherEmail,
					mark.getStudentEmail());
			return false;
		}

		List<MarkEntity> retMark = markRepository.findByStudentAndEvaluatesAndMarkAndSemesterAndDate(student,
				retTeaches, mark.getMark(), mark.getSemester(), mark.getDate());
		if (retMark == null || retMark.isEmpty()) {
			logger.error("Mark {} for student {} in subject {} not found and could not be updated.", mark.getMark(),
					mark.getStudentEmail(), mark.getSubjectName());
			return false;
		}

		markRepository.delete(retMark.get(0));
		logger.info("Mark {} for student {} in subject {} deleted.", mark.getMark(), mark.getStudentEmail(),
				mark.getSubjectName());
		return true;
	}
}
