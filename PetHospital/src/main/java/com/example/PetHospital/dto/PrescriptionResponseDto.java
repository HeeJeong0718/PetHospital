package com.example.PetHospital.dto;

import com.example.PetHospital.domain.Chart;
import com.example.PetHospital.domain.Member;
import com.example.PetHospital.domain.Prescription;
import com.example.PetHospital.domain.Vet;

import lombok.Getter;

@Getter
public class PrescriptionResponseDto {
	private Long id;
	private String description;
	private Chart chart;
	private Vet vet;
	
	public PrescriptionResponseDto(Prescription entity) {
		this.id = entity.getId();
		this.description = entity.getDescription();
		this.chart = entity.getChart();
		this.vet =  entity.getVet();
	}
}
