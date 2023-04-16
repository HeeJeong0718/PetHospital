package com.example.PetHospital.dto;

import com.example.PetHospital.domain.Chart;
import com.example.PetHospital.domain.DepartMent;
import com.example.PetHospital.domain.Vet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor	// 기본 생성자 추가
@AllArgsConstructor
public class VetResponseDto {
	private Long id;
	private String description;
	private String name;
	private DepartMent departMent;
	
	private Long departMentId;
   
	public VetResponseDto(Vet entity) {
		this.id = entity.getId();
		this.description = entity.getDescription();
		this.name = entity.getName();
		this.departMent = entity.getDepartMent();
		this.departMentId = entity.getDepartMent().getId();
	}
}
