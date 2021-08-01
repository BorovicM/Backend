package com.iktpreobuka.eDnevnik.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.eDnevnik.entities.UserEntity;
import com.iktpreobuka.eDnevnik.entities.dto.ResponseUserDto;
import com.iktpreobuka.eDnevnik.entities.dto.ResponseUserTokenDto;
import com.iktpreobuka.eDnevnik.services.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestParam("user") String email, @RequestParam("password") String pwd) {
		ResponseUserTokenDto user = userService.login(email, pwd);
		if (user != null) {
			return new ResponseEntity<ResponseUserTokenDto>(user, HttpStatus.OK);
		}
				
		return new ResponseEntity<String>("Wrong credentials", HttpStatus.UNAUTHORIZED);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(path = "/users", method = RequestMethod.GET)
	public ResponseEntity<?> listUsers() {
		List<ResponseUserDto> lista = new ArrayList<ResponseUserDto>();
		for(UserEntity user : userService.getUsers())
		{
			lista.add(new ResponseUserDto(user.getName(),user.getSurrname(),user.getEmail()));
		}
		return new ResponseEntity<List<ResponseUserDto>>(lista, HttpStatus.OK);
	}
}