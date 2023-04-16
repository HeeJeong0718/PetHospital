package com.example.PetHospital.service;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.PetHospital.domain.Chart;
import com.example.PetHospital.domain.Pet;
import com.example.PetHospital.domain.Prescription;
import com.example.PetHospital.domain.Vet;
import com.example.PetHospital.dto.MemberResponseDto;
import com.example.PetHospital.dto.PrescriptionRequestDto;
import com.example.PetHospital.dto.PrescriptionResponseDto;
import com.example.PetHospital.repository.ChartRepository;
import com.example.PetHospital.repository.PetRepository;
import com.example.PetHospital.repository.PrescriptionRepository;
import com.example.PetHospital.repository.VetRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class PrescriptionService {
    
	private final PrescriptionRepository prescriptionRepository;
	private final VetRepository vetRepository;
	private final ChartRepository chartRepository;
	
	@Transactional
	public PrescriptionRequestDto save(Long vetId, Long chartId, PrescriptionRequestDto requestDto) {
		Vet vet = vetRepository.findById(vetId).get();
	    Chart chart = chartRepository.findById(chartId).get();
 		requestDto.setChart(chart);
 		requestDto.setVet(vet);
 	   prescriptionRepository.save(requestDto.toEntity());
 	   return requestDto;
	}
	
	
	public PrescriptionResponseDto findById(Long id) {
		Prescription pre = prescriptionRepository.findById(id).get();
		return new PrescriptionResponseDto(pre);
	}
	
	
	public List<PrescriptionResponseDto> findAll(){
		return prescriptionRepository.findAll().stream()
				.map(PrescriptionResponseDto::new)
				.collect(Collectors.toList());
	}
	
	@Transactional
	public void delete(Long id) {
	    prescriptionRepository.deleteById(id);
	}
}   
