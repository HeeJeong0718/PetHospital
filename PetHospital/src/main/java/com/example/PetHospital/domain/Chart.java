package com.example.PetHospital.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class Chart extends BaseTimeEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	//private String status;
   @Column(name="status")
   @Enumerated(EnumType.STRING)
    private ChartStatus chartStatus;
   
	@JsonIgnore
	@OneToMany(mappedBy ="chart" , fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Prescription> prescriptions;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name="vet_id")
	private Vet vet;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name="pet_id")
	private Pet pet;
	
	@Builder
	public Chart(ChartStatus chartStatus , Vet vet , Pet pet) {
		this.chartStatus = chartStatus;
		this.vet = vet;
		this.pet =pet;
	}
	public void update(ChartStatus chartStatus, Vet vet) {
		this.chartStatus = chartStatus;
		this.vet = vet;
	
	}
	
	
}
