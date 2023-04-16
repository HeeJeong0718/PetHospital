package com.example.PetHospital.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.PetHospital.dto.DepartMentRequestDto;
import com.example.PetHospital.repository.DepartMentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartMentService {
   
	private final DepartMentRepository departMentRepository;
	
	@Transactional
	public DepartMentRequestDto save(DepartMentRequestDto requestDto) {
		 departMentRepository.save(requestDto.toEntity());
		 return requestDto;
	}
}
