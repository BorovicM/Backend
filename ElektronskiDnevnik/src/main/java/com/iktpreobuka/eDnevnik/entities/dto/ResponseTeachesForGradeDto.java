package com.iktpreobuka.eDnevnik.entities.dto;

public class ResponseTeachesForGradeDto {
	
	private String subjectName;
	
	private String teacherName;
	
	public ResponseTeachesForGradeDto() {}

	public ResponseTeachesForGradeDto(String subjectName, String teacherName) {
		super();
		this.subjectName = subjectName;
		this.teacherName = teacherName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
}
