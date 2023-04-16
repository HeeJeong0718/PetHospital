package com.example.PetHospital.dto;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.example.PetHospital.domain.Chart;
import com.example.PetHospital.domain.Pet;
import com.example.PetHospital.domain.Prescription;
import com.example.PetHospital.domain.Vet;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PrescriptionRequestDto {
	
	private String description;
	private Chart chart;
	private Vet vet;
	
	@Builder
	public PrescriptionRequestDto(String description, Chart chart , Vet vet) {
		this.description = description;
		this.chart = chart;
		this.vet = vet;
	}
   public Prescription toEntity() {
	   return Prescription.builder()
			   .description(description)
			   .chart(chart)
			   .vet(vet)
			   .build();
   }
}
