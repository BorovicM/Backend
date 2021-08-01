package com.iktpreobuka.eDnevnik.controllers;

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

import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.dto.RequestSubjectDto;
import com.iktpreobuka.eDnevnik.services.SubjectService;

@RestController
@RequestMapping(value="/subjects")
public class SubjectController {
	
	@Autowired
	public SubjectService subjectService;
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/newSubject")
	public ResponseEntity<?> createSubject(@Valid @RequestBody RequestSubjectDto subject) {
		SubjectEntity retSub = subjectService.createSubject(subject.getName(), subject.getFundNumber());
		return new ResponseEntity<SubjectEntity>(retSub, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method=RequestMethod.DELETE , value="/deleteSubject")
	public ResponseEntity<?> deleteSubject(@RequestParam String name) {
		if (!subjectService.deleteSubject(name)) {
			return new ResponseEntity<String>("Subject does not exist!", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("Subject deleted!", HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method=RequestMethod.PUT , value="/updateSubject")
	public ResponseEntity<?> updateSubject(@Valid @RequestBody RequestSubjectDto subject) {
		if (!subjectService.updateSubject(subject.getName(), subject.getFundNumber())) {
			return new ResponseEntity<String>("Subject does not exist!", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("Subject updated!", HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method=RequestMethod.GET , value="/getSubjects")
	public ResponseEntity<?> listSubjects() {
		return new ResponseEntity<List<SubjectEntity>>(subjectService.listSubjects(),HttpStatus.OK);
	}
}
