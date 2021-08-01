package com.iktpreobuka.eDnevnik.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;


import com.iktpreobuka.eDnevnik.ElektronskiDnevnikApplication;
import com.iktpreobuka.eDnevnik.Util.Encryption;
import com.iktpreobuka.eDnevnik.entities.AdminEntity;
import com.iktpreobuka.eDnevnik.entities.ParentEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.UserEntity;
import com.iktpreobuka.eDnevnik.entities.dto.ResponseUserTokenDto;
import com.iktpreobuka.eDnevnik.repositories.AdminRepository;
import com.iktpreobuka.eDnevnik.repositories.ParentRepository;
import com.iktpreobuka.eDnevnik.repositories.StudentRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserServiceImpl implements UserService {
	
	private Logger logger = LoggerFactory.getLogger(ElektronskiDnevnikApplication.class.getName());
	
	@Value("${spring.security.secret-key}")
	private String secretKey;
	
	@Value("${spring.security.token-duration}")
	private Integer tokenDuration;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private ParentRepository parentRepository;

	@Override
	public boolean emailExists(String email) {
		if (adminRepository.findByEmail(email) != null || teacherRepository.findByEmail(email) != null ||
				studentRepository.findByEmail(email) != null || parentRepository.findByEmail(email) != null) {
			return true;
		}
		
		return false;
	}

	@Override
	public ResponseUserTokenDto login(String email, String pwd) {
		logger.info("Login called for {}.", email);
		
		AdminEntity adminEntity = adminRepository.findByEmail(email);
		if (adminEntity != null && Encryption.validatePassword(pwd, adminEntity.getPassword())) {
			ResponseUserTokenDto user = new ResponseUserTokenDto(email, getJWTToken(adminEntity));
			return user;
		}
		
		TeacherEntity teacherEntity= teacherRepository.findByEmail(email);
		if (teacherEntity != null && Encryption.validatePassword(pwd, teacherEntity.getPassword())) {
			ResponseUserTokenDto user = new ResponseUserTokenDto(email, getJWTToken(teacherEntity));
			return user;
		}
		
		StudentEntity studentEntity=studentRepository.findByEmail(email);
		if (studentEntity != null && Encryption.validatePassword(pwd, studentEntity.getPassword())) {
			ResponseUserTokenDto user = new ResponseUserTokenDto(email, getJWTToken(studentEntity));
			return user;
		}
		
		ParentEntity parentEntity=parentRepository.findByEmail(email);
		if (parentEntity != null && Encryption.validatePassword(pwd, parentEntity.getPassword())) {
			ResponseUserTokenDto user = new ResponseUserTokenDto(email, getJWTToken(parentEntity));
			return user;
		}
		
		return null;
	}
	
	@Override
	public List<UserEntity> getUsers() {
		List<UserEntity> list = new ArrayList<UserEntity>();
		adminRepository.findAll().forEach(e->list.add((UserEntity)e));
		teacherRepository.findAll().forEach(e->list.add((UserEntity)e));
		studentRepository.findAll().forEach(e->list.add((UserEntity)e));
		parentRepository.findAll().forEach(e->list.add((UserEntity)e));
		return list;
	}
	
	private String getJWTToken(UserEntity userEntity) {
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(userEntity.getRole().toString());
		String token = Jwts.builder().setId("softtekJWT").setSubject(userEntity.getEmail())
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + this.tokenDuration))
				.signWith(SignatureAlgorithm.HS512, this.secretKey.getBytes()).compact();
		return "Bearer " + token;
	}
}
