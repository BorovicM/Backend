package com.iktpreobuka.eDnevnik.controllers;

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

import com.iktpreobuka.eDnevnik.entities.MarkEntity;
import com.iktpreobuka.eDnevnik.entities.dto.GradeDto;
import com.iktpreobuka.eDnevnik.entities.dto.RequestMarkDto;
import com.iktpreobuka.eDnevnik.entities.dto.RequestMarkWithDateDto;
import com.iktpreobuka.eDnevnik.entities.dto.RespnoseMarkWithGradeDto;
import com.iktpreobuka.eDnevnik.entities.dto.RequestUpdateMarkDto;
import com.iktpreobuka.eDnevnik.entities.dto.ResponseMarkDto;
import com.iktpreobuka.eDnevnik.services.MarkService;

@RestController
public class MarkControllers {

	@Autowired
	private MarkService markService;
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/newMark")
	public ResponseEntity<?> createMark(@Valid @RequestBody RequestMarkDto mark) {
		boolean retMark = markService.createMark(mark);
		
		if (!retMark) {
			return new ResponseEntity<String>("Mark is not added, error occured!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<String>("Mark successfully added!", HttpStatus.OK);
	}
	
	@Secured("ROLE_TEACHER")
	@RequestMapping(method = RequestMethod.POST, value = "/newMarkTeacher")
	public ResponseEntity<?> createMarkForTeacher(@Valid @RequestBody RequestMarkDto mark) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		boolean retMark = markService.createMarkTeacher(mark, email);
		
		if (!retMark) {
			return new ResponseEntity<String>("Mark is not added, error occured!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<String>("Mark successfully added!", HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteMark")
	public ResponseEntity<?> deleteMark(@Valid @RequestBody RequestMarkWithDateDto mark) {
		boolean retMark = markService.deleteMark(mark);
		
		if (retMark == false) {
			return new ResponseEntity<String>("Mark is not deleted!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<String>("Mark is deleted!", HttpStatus.OK);
	}
	@Secured("ROLE_TEACHER")
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteMarkForTeacher")
	public ResponseEntity<?> deleteMarkForTeacher(@Valid @RequestBody RequestMarkWithDateDto mark) {
	String email = SecurityContextHolder.getContext().getAuthentication().getName();
	boolean retMark = markService.deleteMarkForTeacher(mark, email);

	if (retMark == false) {
	return new ResponseEntity<String>("Mark is not deleted!", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	return new ResponseEntity<String>("Mark is deleted!", HttpStatus.OK);
	}
	
	@Secured("ROLE_TEACHER")
	@RequestMapping(method = RequestMethod.PUT, value = "/updateMarkTeacher")
	public ResponseEntity<?> updateMarkForTeacher(@Valid @RequestBody RequestUpdateMarkDto mark) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		boolean retMark = markService.updateMarkTeacher(mark, email);
		
		if (!retMark) {
			return new ResponseEntity<String>("Mark is not updated, error occured!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<String>("Mark successfully updated!", HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/getMarks")
	public ResponseEntity<?> listMarks() {
		return new ResponseEntity<List<MarkEntity>>(markService.getMarks(), HttpStatus.OK);
	}
	
	@Secured("ROLE_STUDENT")
	@RequestMapping(method = RequestMethod.GET, value = "/getMyMarks")
	public ResponseEntity<?> listMyMarks() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return new ResponseEntity<List<ResponseMarkDto>>(markService.getMarksForStudent(email), HttpStatus.OK);
	}
	
	@Secured("ROLE_PARENT")
	@RequestMapping(method = RequestMethod.GET, value = "/getKidMarks")
	public ResponseEntity<?> listKidMarks() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return new ResponseEntity<List<ResponseMarkDto>>(markService.getMarksForParent(email), HttpStatus.OK);
	}
	
	@Secured("ROLE_TEACHER")
	@RequestMapping(method = RequestMethod.GET, value = "/getMarksForGrade")
	public ResponseEntity<?> listMarksForGrade(@Valid @RequestParam GradeDto grade) {
		return new ResponseEntity<List<ResponseMarkDto>>(markService.getMarksForGrade(grade.getGrade(), grade.getGradeMark()), HttpStatus.OK);
	}
	
	@Secured("ROLE_TEACHER")
	@RequestMapping(method = RequestMethod.GET, value = "/getMarksForGradeByTeacher")
	public ResponseEntity<?> listMarksForGradeBuTeacher(@Valid @RequestBody GradeDto grade) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return new ResponseEntity<List<ResponseMarkDto>>(markService.getMarksForTeacherInGrade(email, grade.getGrade(), grade.getGradeMark()), HttpStatus.OK);
	}
	
	@Secured("ROLE_TEACHER")
	@RequestMapping(method = RequestMethod.GET, value = "/getMarksByTeacher")
	public ResponseEntity<?> listMarksByTeacher() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return new ResponseEntity<List<RespnoseMarkWithGradeDto>>(markService.getMarksByTeacher(email), HttpStatus.OK);
	}
	
	@Secured("ROLE_STUDENT")
	@RequestMapping(method = RequestMethod.GET, value = "/getMyMarksOnSubject")
	public ResponseEntity<?> listMyMarksOnSubject(@RequestParam String subjectName) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return new ResponseEntity<List<ResponseMarkDto>>(markService.getMarksForStudentOnSubject(email, subjectName), HttpStatus.OK);
	}
}
