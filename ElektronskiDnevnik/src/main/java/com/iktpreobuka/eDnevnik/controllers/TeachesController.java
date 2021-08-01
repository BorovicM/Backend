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

import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeachesEntity;
import com.iktpreobuka.eDnevnik.entities.dto.GradeDto;
import com.iktpreobuka.eDnevnik.entities.dto.RequestTeachesDto;
import com.iktpreobuka.eDnevnik.entities.dto.ResponseTeachesDto;
import com.iktpreobuka.eDnevnik.entities.dto.ResponseUserDto;
import com.iktpreobuka.eDnevnik.services.TeachesService;

@RestController
@RequestMapping(value = "/teach")
public class TeachesController {

	@Autowired
	public TeachesService teachesService;

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/newTeaches")
	public ResponseEntity<?> createTeaches(@Valid @RequestBody RequestTeachesDto teaches) {
		TeachesEntity retTeaches = teachesService.createTeaches(teaches.getGrade(), teaches.getGradeMark(),
				teaches.getTeacherEmail(), teaches.getSubjectName());
		if (retTeaches == null) {
			return new ResponseEntity<String>("Grade, Teacher or Subject does not exist!", HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<TeachesEntity>(retTeaches, HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteTeaches")
	public ResponseEntity<?> deleteTeaches(@Valid @RequestBody RequestTeachesDto teaches) {
		if (!teachesService.deleteTeaches(teaches.getGrade(), teaches.getGradeMark(), teaches.getTeacherEmail(),
				teaches.getSubjectName())) {
			return new ResponseEntity<String>("Teaches does not exist!", HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<String>("Teaches deleted!", HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/getTeaches")
	public ResponseEntity<?> listTeaches() {
		List<ResponseTeachesDto> list = new ArrayList<ResponseTeachesDto>();
		for (TeachesEntity teaches : teachesService.listTeaches()) {
			list.add(new ResponseTeachesDto(teaches.getGrade().getGrade(), teaches.getGrade().getMark(),
					teaches.getSubject().getName(),
					teaches.getTeacher().getName() + " " + teaches.getTeacher().getSurrname()));
		}

		return new ResponseEntity<List<ResponseTeachesDto>>(list, HttpStatus.OK);
	}

	@Secured("ROLE_TEACHER")
	@RequestMapping(method = RequestMethod.GET, value = "/getSubjects")
	public ResponseEntity<?> listSubjectsByTeacher() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return new ResponseEntity<List<SubjectEntity>>(teachesService.listOfTeacherSubjects(email), HttpStatus.OK);
	}

	@Secured("ROLE_TEACHER")
	@RequestMapping(method = RequestMethod.GET, value = "/getGrades")
	public ResponseEntity<?> listGradesByTeacher() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return new ResponseEntity<List<GradeDto>>(teachesService.listOfTeacherGrades(email), HttpStatus.OK);
	}

	@Secured("ROLE_PARENT")
	@RequestMapping(method = RequestMethod.GET, value = "/getTeacher")
	public ResponseEntity<?> getTeacherOnSubjectinGrade(@RequestParam int grade, @RequestParam int markGrade,
			@RequestParam String nameSubject) {
		ResponseUserDto retUser = teachesService.getTeacherOnSubjectinGrade(nameSubject, grade, markGrade);
		if (retUser == null) {
			return new ResponseEntity<String>("Grade or Subject does not exist!", HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ResponseUserDto>(retUser, HttpStatus.OK);
	}
}
