package com.iktpreobuka.eDnevnik.entities.dto;

import java.util.List;

public class ResponseTeacherDto extends ResponseUserDto {
	
	public List<ResponseTeachesDto> teaches;
	
	public ResponseTeacherDto() {}

	public ResponseTeacherDto(String name, String lastName, String email, List<ResponseTeachesDto> teaches) {
		super(name, lastName, email);
		this.teaches = teaches;
	}

	public List<ResponseTeachesDto> getTeaches() {
		return teaches;
	}

	public void setTeaches(List<ResponseTeachesDto> teaches) {
		this.teaches = teaches;
	}
}
