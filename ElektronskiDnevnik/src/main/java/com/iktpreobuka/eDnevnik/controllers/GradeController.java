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

import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.TeachesEntity;
import com.iktpreobuka.eDnevnik.entities.dto.GradeDto;
import com.iktpreobuka.eDnevnik.entities.dto.ResponseGradeDto;
import com.iktpreobuka.eDnevnik.entities.dto.ResponseTeachesForGradeDto;
import com.iktpreobuka.eDnevnik.entities.dto.ResponseUserDto;
import com.iktpreobuka.eDnevnik.services.GradeService;

@RestController
@RequestMapping(value = "/grade")
public class GradeController {

	@Autowired
	public GradeService gradeService;

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/newGrade")
	public ResponseEntity<?> createGrade(@Valid @RequestBody GradeDto grade) {
		GradeEntity retGrade = gradeService.createGrade(grade.getGrade(), grade.getGradeMark());
		return new ResponseEntity<GradeEntity>(retGrade, HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteGrade")
	public ResponseEntity<?> deleteGrade(@Valid @RequestBody GradeDto grade) {
		if (!gradeService.deleteGrade(grade.getGrade(), grade.getGradeMark())) {
			return new ResponseEntity<String>("Grade does not exist!", HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<String>("Grade deleted!", HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/getGrades")
	public ResponseEntity<?> listGrades() {
		List<ResponseGradeDto> list = new ArrayList<ResponseGradeDto>();
		for (GradeEntity grade : gradeService.listGrades()) {
			ResponseGradeDto resGrade = new ResponseGradeDto(grade.getGrade(), grade.getMark());
			for (TeachesEntity teaches : grade.getTeachs()) {
				resGrade.getTeaches().add(new ResponseTeachesForGradeDto(teaches.getSubject().getName(),
						teaches.getTeacher().getName() + " " + teaches.getTeacher().getSurrname()));
			}

			for (StudentEntity student : grade.getStudents()) {
				resGrade.getStudents()
						.add(new ResponseUserDto(student.getName(), student.getSurrname(), student.getEmail()));
			}

			list.add(resGrade);
		}

		return new ResponseEntity<List<ResponseGradeDto>>(list, HttpStatus.OK);
	}
}
