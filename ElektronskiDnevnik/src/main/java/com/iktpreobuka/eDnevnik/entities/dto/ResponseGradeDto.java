package com.iktpreobuka.eDnevnik.entities.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponseGradeDto extends GradeDto {
	
	private List<ResponseTeachesForGradeDto> teaches;
	
	private List<ResponseUserDto> students;
	
	public ResponseGradeDto() {}
	
	public ResponseGradeDto(int grade, int gradeMark) {
		super(grade, gradeMark);
		this.teaches = new ArrayList<ResponseTeachesForGradeDto>();
		this.students = new ArrayList<ResponseUserDto>();
	}

	public ResponseGradeDto(int grade, int gradeMark, List<ResponseTeachesForGradeDto> teaches,
			List<ResponseUserDto> students) {
		super(grade, gradeMark);
		this.teaches = teaches;
		this.students = students;
	}

	public List<ResponseTeachesForGradeDto> getTeaches() {
		return teaches;
	}

	public void setTeaches(List<ResponseTeachesForGradeDto> teaches) {
		this.teaches = teaches;
	}

	public List<ResponseUserDto> getStudents() {
		return students;
	}

	public void setMarks(List<ResponseUserDto> students) {
		this.students = students;
	}
}
