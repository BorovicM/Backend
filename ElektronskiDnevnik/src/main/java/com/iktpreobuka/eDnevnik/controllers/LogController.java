package com.iktpreobuka.eDnevnik.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/downloadLogs")
	public ResponseEntity<?> downloadLogs() {
		try {			
			File file = new File("logs/spring-boot-logging.log");
			
			HttpHeaders headers = new HttpHeaders();
	        headers.add("content-disposition", "attachment; filename=log.txt");
	        
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			
			return ResponseEntity.ok()
		            .headers(headers)
		            .contentLength(file.length())
		            .contentType(MediaType.APPLICATION_OCTET_STREAM)
		            .body(resource);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<String>("Log file not found!", HttpStatus.NOT_FOUND);
	}
}
