package com.example.PetHospital.dto;

import com.example.PetHospital.domain.Chart;
import com.example.PetHospital.domain.ChartStatus;
import com.example.PetHospital.domain.Pet;
import com.example.PetHospital.domain.Vet;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChartRequestDto {
	private Long id;
	//private String status;
	private ChartStatus chartStatus;
	private Vet vet;
	private Pet pet;
	
	@Builder
	public ChartRequestDto( ChartStatus chartStatus, Vet vet , Pet pet) {
		this.chartStatus = chartStatus;
		this.vet = vet;
		this.pet = pet;
	}
	
	public Chart toEntity() {
		return Chart.builder()
				.chartStatus(chartStatus)
				.vet(vet)
				.pet(pet)
				.build();
				
	}
}
