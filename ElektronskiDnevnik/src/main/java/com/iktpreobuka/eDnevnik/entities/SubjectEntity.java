package com.iktpreobuka.eDnevnik.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="Subject")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SubjectEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(unique=true)
	private String name;
	
	@Column
	private int fundHours;
	
	@OneToMany (mappedBy="subject",cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonBackReference (value="Subt")
	private List<TeachesEntity> teaches;
	
	public SubjectEntity() {}
	
	public SubjectEntity(String name, int fundHours) {
		super();
		this.name = name;		
		this.fundHours = fundHours;
		this.teaches = new ArrayList<TeachesEntity>();
	}
		
	public SubjectEntity(String name, int fundHours, List<TeachesEntity> teaches) {
		super();
		this.name = name;		
		this.fundHours = fundHours;
		this.teaches=teaches;
	}
	
	public List<TeachesEntity> getTeaches() {
		return teaches;
	}

	public void setTeaches(List<TeachesEntity> teaches) {
		this.teaches = teaches;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getFundHours() {
		return fundHours;
	}
	
	public void setFundHours(int fundHours) {
		this.fundHours = fundHours;
	}
}
