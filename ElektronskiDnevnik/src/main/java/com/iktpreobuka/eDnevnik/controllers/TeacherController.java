package com.iktpreobuka.eDnevnik.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.TeachesEntity;
import com.iktpreobuka.eDnevnik.entities.dto.RequestNewUserDto;
import com.iktpreobuka.eDnevnik.entities.dto.RequestUpdateUserDto;
import com.iktpreobuka.eDnevnik.entities.dto.ResponseTeacherDto;
import com.iktpreobuka.eDnevnik.entities.dto.ResponseTeachesDto;
import com.iktpreobuka.eDnevnik.services.TeacherService;
import com.iktpreobuka.eDnevnik.services.UserService;

@RestController
@RequestMapping(value="/users")
public class TeacherController {

	@Autowired
	public TeacherService teacherService;
	
	@Autowired
	private UserService userService;
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/newTeacher")
	public ResponseEntity<?> createTeacher(@Valid @RequestBody RequestNewUserDto user) {
		if (userService.emailExists(user.getEmail())) {
			return new ResponseEntity<String>("Email is already in use, pick another!", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<TeacherEntity>(teacherService.createTeacher(user.getName(), user.getEmail(), user.getLastName(), user.getPassword()), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method=RequestMethod.DELETE , value="/deleteTeacher")
	public ResponseEntity<?> deleteTeacher(@RequestParam String email) {
		if (!teacherService.deleteTeacher(email)) {
			return new ResponseEntity<String>("Teacher does not exist!", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("Teacher deleted!", HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method=RequestMethod.PUT , value="/updateTeacher")
	public ResponseEntity<?> updateTeacher(@Valid @RequestBody RequestUpdateUserDto user) {
		if (!teacherService.updateTeacher(user.getName(), user.getEmail(), user.getLastName(), user.getPassword())) {
			return new ResponseEntity<String>("Teacher does not exist!", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("Teacher updated!", HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method=RequestMethod.GET , value="/getTeachers")
	public ResponseEntity<?> listUsers() {
	List<ResponseTeacherDto> list = new ArrayList<ResponseTeacherDto>();
	for (TeacherEntity user : teacherService.listUsers()) {
	ResponseTeacherDto teacher = new ResponseTeacherDto(user.getName(),user.getSurrname(),user.getEmail(), new ArrayList<ResponseTeachesDto>());
	for (TeachesEntity teaches : user.getTeaches()) {
	teacher.getTeaches().add(new ResponseTeachesDto(teaches.getGrade().getGrade(), teaches.getGrade().getMark(), teaches.getSubject().getName(), teaches.getTeacher().getName()+" "+teaches.getTeacher().getSurrname()));
	}

	list.add(teacher);
	}

	return new ResponseEntity<List<ResponseTeacherDto>>(list, HttpStatus.OK);
	}
}
