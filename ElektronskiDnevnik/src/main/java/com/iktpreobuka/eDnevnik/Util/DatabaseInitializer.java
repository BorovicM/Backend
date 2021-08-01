//package com.iktpreobuka.eDnevnik.Util;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import com.iktpreobuka.eDnevnik.entities.AdminEntity;
//import com.iktpreobuka.eDnevnik.entities.GradeEntity;
//import com.iktpreobuka.eDnevnik.entities.MarkEntity;
//import com.iktpreobuka.eDnevnik.entities.ParentEntity;
//import com.iktpreobuka.eDnevnik.entities.StudentEntity;
//import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
//import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
//import com.iktpreobuka.eDnevnik.entities.TeachesEntity;
//import com.iktpreobuka.eDnevnik.repositories.AdminRepository;
//import com.iktpreobuka.eDnevnik.repositories.GradeRepository;
//import com.iktpreobuka.eDnevnik.repositories.MarkRepository;
//import com.iktpreobuka.eDnevnik.repositories.ParentRepository;
//import com.iktpreobuka.eDnevnik.repositories.StudentRepository;
//import com.iktpreobuka.eDnevnik.repositories.SubjectRepository;
//import com.iktpreobuka.eDnevnik.repositories.TeacherRepository;
//import com.iktpreobuka.eDnevnik.repositories.TeachesRepository;
//
//@Component
//public class DatabaseInitializer implements ApplicationRunner {
//
//	@Autowired
//	AdminRepository adminRepository;
//	
//	@Autowired
//	TeacherRepository teacherRepository;
//	
//	@Autowired
//	StudentRepository studentRepository;
//	
//	@Autowired
//	ParentRepository parentRepository;
//	
//	@Autowired
//	GradeRepository gradeRepository;
//	
//	@Autowired
//	SubjectRepository subjectRepository;
//	
//	@Autowired
//	TeachesRepository teachesRepository;
//	
//	@Autowired
//	MarkRepository markRepository;
//	
//	@Override
//    public void run(ApplicationArguments args) throws Exception{
//		adminRepository.save(new AdminEntity("admin", "admin", "admin@gmail.com", Encryption.getPassEncoded("pass")));
//		TeacherEntity teacher = teacherRepository.save(new TeacherEntity("teacher", "teacher", "teacher@gmail.com", Encryption.getPassEncoded("pass")));
//		TeacherEntity teacher2 = teacherRepository.save(new TeacherEntity("teacher2", "teacher2", "teacher2@gmail.com", Encryption.getPassEncoded("pass")));
//		
//		GradeEntity grade = gradeRepository.save(new GradeEntity(1,1));
//		
//		StudentEntity student = studentRepository.save(new StudentEntity("student", "student", "student@gmail.com", Encryption.getPassEncoded("pass"), grade));
//		studentRepository.save(new StudentEntity("student1", "student1", "student1@gmail.com", Encryption.getPassEncoded("pass"), grade));
//		ArrayList<StudentEntity> kids = new ArrayList<StudentEntity>();
//		kids.add(student);
//		ParentEntity parent = parentRepository.save(new ParentEntity("parent", "parent", "borovicns95@gmail.com", Encryption.getPassEncoded("pass"), kids));
//		student.setParent(parent);
//		studentRepository.save(student);
//		
//		SubjectEntity sub1 = subjectRepository.save(new SubjectEntity("matematika", 30));
//		SubjectEntity sub2 = subjectRepository.save(new SubjectEntity("fizika", 20));
//		
//		TeachesEntity teaches = teachesRepository.save(new TeachesEntity(grade, teacher, sub1));
//		teachesRepository.save(new TeachesEntity(grade, teacher2, sub2));
//		
//		markRepository.save(new MarkEntity(student, teaches, 5, 1, LocalDate.now()));
//		markRepository.save(new MarkEntity(student, teaches, 4, 1, LocalDate.now()));
//		markRepository.save(new MarkEntity(student, teaches, 5, 1, LocalDate.now()));
//	}
//}
