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

import com.iktpreobuka.eDnevnik.entities.ParentEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.dto.RequestNewUserDto;
import com.iktpreobuka.eDnevnik.entities.dto.RequestUpdateUserDto;
import com.iktpreobuka.eDnevnik.entities.dto.ResponseParentDto;
import com.iktpreobuka.eDnevnik.entities.dto.ResponseUserDto;
import com.iktpreobuka.eDnevnik.services.ParentService;
import com.iktpreobuka.eDnevnik.services.UserService;

@RestController
@RequestMapping(value="/users")
public class ParentController {
	
	@Autowired
	ParentService  parentService;
	
	@Autowired
	private UserService userService;
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/newParent")
	public ResponseEntity<?> addParent(@Valid @RequestBody RequestNewUserDto user) {
		if (userService.emailExists(user.getEmail())) {
			return new ResponseEntity<String>("Email is already in use, pick another!", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<ParentEntity>(parentService.addParent(user.getName(), user.getEmail(), user.getLastName(), user.getPassword()), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method=RequestMethod.DELETE , value="/deleteParent")
	public ResponseEntity<?> deleteParent(@RequestParam String email) {
		if (!parentService.deleteParent(email)) {
			return new ResponseEntity<String>("Parent does not exist!", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("Parent deleted!", HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method=RequestMethod.PUT , value="/updateParent")
	public ResponseEntity<?> updateParent(@Valid @RequestBody RequestUpdateUserDto user) {
		if (!parentService.updateParent(user.getName(), user.getEmail(), user.getLastName(), user.getPassword())) {
			return new ResponseEntity<String>("Parent does not exist!", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("Parent updated!", HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method=RequestMethod.GET , value="/getParents")
	public ResponseEntity<?> listUsers() {
	List<ResponseParentDto> list = new ArrayList<ResponseParentDto>();
	for (ParentEntity user : parentService.listUsers()) {
		ResponseParentDto parent = new ResponseParentDto(user.getName(),user.getSurrname(),user.getEmail(), new ArrayList<ResponseUserDto>());
	for (StudentEntity kid : user.getKid()) {
	parent.getKids().add(new ResponseUserDto(kid.getName(),kid.getSurrname(),kid.getEmail()));
	}

	list.add(parent);
	}

	return new ResponseEntity<List<ResponseParentDto>>(list, HttpStatus.OK);
	}
}
