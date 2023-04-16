package com.example.PetHospital.dto;

import com.example.PetHospital.domain.DepartMent;
import com.example.PetHospital.domain.Vet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VetUpdateResponseDto {
	private String description;
	private String name;
	private DepartMent departMent;
	private Long departMentId;
  
	public VetUpdateResponseDto(Vet entity) {
		this.description = entity.getDescription();
		this.name = entity.getName();
		this.departMent = entity.getDepartMent();
		this.departMentId = entity.getDepartMent().getId();
	}
}
