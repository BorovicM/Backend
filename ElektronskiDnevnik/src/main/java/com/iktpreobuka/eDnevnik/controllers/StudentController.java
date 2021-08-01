package com.iktpreobuka.eDnevnik.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.dto.RequestNewUserDto;
import com.iktpreobuka.eDnevnik.entities.dto.RequestUpdateUserDto;
import com.iktpreobuka.eDnevnik.entities.dto.ResponseStudentDto;
import com.iktpreobuka.eDnevnik.entities.dto.ResponseUserDto;
import com.iktpreobuka.eDnevnik.services.StudentService;
import com.iktpreobuka.eDnevnik.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class StudentController {
	
	@Autowired
	public StudentService studentService;
	
	@Autowired
	private UserService userService;

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/newStudent")
	public ResponseEntity<?> createStudent(@Valid @RequestBody RequestNewUserDto user) {
		if (userService.emailExists(user.getEmail())) {
			return new ResponseEntity<String>("Email is already in use, pick another!", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<StudentEntity>(studentService.createStudent(user.getName(), user.getEmail(), user.getLastName(), user.getPassword()), HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteStudent")
	public ResponseEntity<?> deleteStudent(@RequestParam String email) {
		if (!studentService.deleteStudent(email)) {
			return new ResponseEntity<String>("Student does not exist!", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("Student deleted!", HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "/updateStudent")
	public ResponseEntity<?> updateStudent(@Valid @RequestBody RequestUpdateUserDto user) {
		if (!studentService.updateStudent(user.getName(), user.getEmail(), user.getLastName(), user.getPassword())) {
			return new ResponseEntity<String>("Student does not exist!", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("Student updated!", HttpStatus.OK);	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/getStudents")
	public ResponseEntity<?> listUsers() {
		List<ResponseStudentDto> lista = new ArrayList<ResponseStudentDto>();
		for(StudentEntity user : studentService.listUsers())
		{
			String parent="";
			if(user.getParent()!=null) {
				parent=user.getParent().getName()+" "+user.getParent().getSurrname();
			}
			int grade=0;
			int gradeMark=0;
			if(user.getGrade()!=null) {
				grade=user.getGrade().getGrade();
				gradeMark=user.getGrade().getMark();
			}
			
			lista.add(new ResponseStudentDto(user.getName(),user.getSurrname(),user.getEmail(),parent ,grade,gradeMark));
		}
		return new ResponseEntity<List<ResponseStudentDto>>(lista, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value="/addStudentToGrade")
	public ResponseEntity<?> addStudentToGrade(@RequestParam String email, @RequestParam int grade, @RequestParam int markGrade){
		if(studentService.addStudentToGrade(email, grade, markGrade)) {
			return new ResponseEntity<String>("Student successfully added to grade!",HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("Student or Grade does not exist!", HttpStatus.NOT_FOUND);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value="/addParentToStudent")
	public ResponseEntity<?> addParentToStudent(@RequestParam String email, @RequestParam String parentEmail) {
		if(studentService.addParentToStudent(email, parentEmail)) {
			return new ResponseEntity<String>("Parent successfully added to student!",HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("Student or Parent does not exist!", HttpStatus.NOT_FOUND);
	}
	
	@Secured({"ROLE_STUDENT","ROLE_ADMIN"})
	@RequestMapping(method=RequestMethod.GET , value="/getStudentSubjects")
	public ResponseEntity<?> listSubjectsByStudent() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return new ResponseEntity<List<SubjectEntity>>(studentService.listOfStudentSubjects(email), HttpStatus.OK);
	}
	
	@Secured({"ROLE_TEACHER","ROLE_ADMIN"})
	@RequestMapping(method=RequestMethod.GET , value="/getStudentParent")
	public ResponseEntity<?> getParentOfStudent(@RequestParam String email) {
		ResponseUserDto parent = studentService.getParent(email);
		if (parent == null) {
			return new ResponseEntity<String>("Student does not exist or does not have parent associated!", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ResponseUserDto>(parent, HttpStatus.OK);
	}
}
