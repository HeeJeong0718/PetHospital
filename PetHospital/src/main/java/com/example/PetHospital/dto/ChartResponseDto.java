package com.example.PetHospital.dto;

import java.util.List;

import com.example.PetHospital.domain.Chart;
import com.example.PetHospital.domain.ChartStatus;
import com.example.PetHospital.domain.DepartMent;
import com.example.PetHospital.domain.Pet;
import com.example.PetHospital.domain.Prescription;
import com.example.PetHospital.domain.Vet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor	// 기본 생성자 추가
@AllArgsConstructor
public class ChartResponseDto {
	
	//private String status;
	private Long id;
	private ChartStatus chartStatus;
	private List<Prescription> prescriptions;
	private Vet vet;
	private Pet pet;
	private Long vetId;
	
    public ChartResponseDto(Chart entity) {
    	this.id = entity.getId();
    	this.chartStatus = entity.getChartStatus();
    	this.prescriptions = entity.getPrescriptions();
    	this.vet = entity.getVet();
    	this.pet = entity.getPet();
    	this.vetId = entity.getVet().getId();
    }
}
