package com.example.PetHospital.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.PetHospital.domain.Chart;
import com.example.PetHospital.domain.Pet;
import com.example.PetHospital.domain.Vet;
import com.example.PetHospital.dto.ChartRequestDto;
import com.example.PetHospital.dto.ChartResponseDto;
import com.example.PetHospital.repository.ChartRepository;
import com.example.PetHospital.repository.PetRepository;
import com.example.PetHospital.repository.VetRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChartService {
     
	private final ChartRepository chartRepository;
	private final VetRepository vetRepository;
	private final PetRepository petRepository;
	
	@Transactional
	public ChartRequestDto save(Long petId,Long vetId, ChartRequestDto requestDto) {
		//펫 id 를 가져온다
		Pet pet = petRepository.findById(petId).get();
		Vet vet = vetRepository.findById(vetId).get();
		requestDto.setPet(pet);
		requestDto.setVet(vet);
		 chartRepository.save(requestDto.toEntity());
		 return requestDto;
	}
	
	//한개가져오기
	
	public ChartResponseDto findById(Long id) {
		Chart chart = chartRepository.findById(id).get();
		return new ChartResponseDto(chart);
	}
	@Transactional
	public ChartResponseDto modify(Long id, ChartResponseDto requestDto) {
		Chart chart = chartRepository.findById(id).get();
	   	Vet vet  = vetRepository.findById(requestDto.getVetId()).get();
		 chart.update(requestDto.getChartStatus(),vet);
		return requestDto;
	}
	
	
}
