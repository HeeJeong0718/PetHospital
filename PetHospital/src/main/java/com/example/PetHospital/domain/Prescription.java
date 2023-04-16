package com.example.PetHospital.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class Prescription extends BaseTimeEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	@JsonIgnore
	  @ManyToOne
	 @JoinColumn(name="chart_id")
	private Chart chart;
	//vet만참소 단방향
	  
	  @OneToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JoinColumn(name="vet_id")
	private Vet vet;
	
	@Builder
	public Prescription(String description, Chart chart, Vet vet){
		this.description = description;
		this.chart = chart;
		this.vet = vet;
	}
} 
