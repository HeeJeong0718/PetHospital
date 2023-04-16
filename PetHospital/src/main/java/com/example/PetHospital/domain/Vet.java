package com.example.PetHospital.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class Vet extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	private String name;
	
	@Setter
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name="department_id")
	private DepartMent departMent;
		
	
	@Builder
	public Vet(String description , String name , DepartMent departMent) {
		this.description = description;
		this.name = name;
		this.departMent = departMent;
	}
	
	public void update(String description , String name ,DepartMent departMent) {
		this.description = description;
		this.name = name;
		this.departMent = departMent;
	}
	@Override
	public String toString() {
		return "name" + this.getName() + "description" + this.getDescription() + "departMent" + this.getDepartMent();
	}
}
