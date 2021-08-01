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

import com.iktpreobuka.eDnevnik.Util.Encryption;
import com.iktpreobuka.eDnevnik.entities.AdminEntity;
import com.iktpreobuka.eDnevnik.entities.dto.RequestNewUserDto;
import com.iktpreobuka.eDnevnik.entities.dto.RequestUpdateUserDto;
import com.iktpreobuka.eDnevnik.services.AdminService;
import com.iktpreobuka.eDnevnik.services.UserService;

@RestController
public class AdminControllers {
		
	@Autowired
	public AdminService adminService;
	
	@Autowired
	private UserService userService;
		
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/newAdmin")
	public ResponseEntity<?> createAdmin(@Valid @RequestBody RequestNewUserDto user) {
		if (userService.emailExists(user.getEmail())) {
			return new ResponseEntity<String>("Email is already in use, pick another!", HttpStatus.BAD_REQUEST);
		}
		
		AdminEntity retUser = adminService.createAdmin(user.getName(), user.getLastName(),
				user.getEmail(), Encryption.getPassEncoded(user.getPassword()));
		return new ResponseEntity<AdminEntity>(retUser, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method=RequestMethod.DELETE , value="/deleteAdmin")
	public ResponseEntity<?> deleteAdmin(@RequestParam String email) {
		if(adminService.deleteAdmin(email)) {
			return new ResponseEntity<String>("User deleted!", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("Admin does not exist!", HttpStatus.NOT_FOUND);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method=RequestMethod.PUT , value="/updateAdmin")
	public ResponseEntity<?> updateAdmin(@Valid @RequestBody RequestUpdateUserDto user) {	
		if(!adminService.updateAdmin(user.getName(), user.getEmail(), user.getLastName(), user.getPassword())) {
			return new ResponseEntity<String>("Admin does not exist!", HttpStatus.NOT_FOUND); 
		}
		
		return new ResponseEntity<String>("User updated!", HttpStatus.OK);
	}
		
	@Secured("ROLE_ADMIN")
	@RequestMapping(method=RequestMethod.GET , value="/getAdmins")
	public ResponseEntity<?> listUsers() {
		return new ResponseEntity<List<AdminEntity>>(adminService.listUsers(), HttpStatus.OK);
	}
}
