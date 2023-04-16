package com.example.PetHospital.dto;

import com.example.PetHospital.domain.DepartMent;
import com.example.PetHospital.domain.Vet;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VetRequestDto {
  
	private String description;
	private String name;
	private DepartMent departMent;
	
	@Builder
	public VetRequestDto(String description, String name , DepartMent departMent) {
		this.description  =  description;
		this.name = name;
		this.departMent = departMent;
	}
	public Vet toEntity() {
		return Vet.builder()
				.description(description)
				.name(name)
			    .departMent(departMent)
				.build();
	}
}
