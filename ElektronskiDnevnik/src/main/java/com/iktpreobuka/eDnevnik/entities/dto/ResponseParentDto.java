package com.iktpreobuka.eDnevnik.entities.dto;

import java.util.List;

public class ResponseParentDto extends ResponseUserDto {

	private List<ResponseUserDto> kids;
	
	public ResponseParentDto() {}

	public ResponseParentDto(String name, String lastName, String email, List<ResponseUserDto> kids) {
		super(name, lastName, email);
		this.kids = kids;
	}

	public List<ResponseUserDto> getKids() {
		return kids;
	}

	public void setKids(List<ResponseUserDto> kids) {
		this.kids = kids;
	}
}
