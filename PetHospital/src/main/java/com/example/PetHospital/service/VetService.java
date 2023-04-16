package com.example.PetHospital.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.PetHospital.domain.DepartMent;
import com.example.PetHospital.domain.Vet;
import com.example.PetHospital.dto.VetRequestDto;
import com.example.PetHospital.dto.VetResponseDto;
import com.example.PetHospital.repository.DepartMentRepository;
import com.example.PetHospital.repository.VetRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VetService {
    private final VetRepository vetRepository;
    private final DepartMentRepository DepartMentRepository;
    
    @Transactional
    public VetRequestDto saveVet(Long depId, VetRequestDto requestDto) {
    	DepartMent dep = DepartMentRepository.findById(depId).get();
    	requestDto.setDepartMent(dep);
        vetRepository.save(requestDto.toEntity());
        return requestDto;
    }

  	public List<VetResponseDto> findAll(){
  		return vetRepository.findAll().stream()
  				.map(VetResponseDto::new)
  				.collect(Collectors.toList());
  	}
  	
  	public VetResponseDto findById(Long id) {
  		Vet vet = vetRepository.findById(id).get();
  		return new VetResponseDto(vet);
  	}
   @Transactional
   public VetResponseDto modifyVet(Long vetId,  VetResponseDto requestDto) {
	   
	   Vet vet = vetRepository.findById(vetId).get();
	   DepartMent dep = DepartMentRepository.findById(requestDto.getDepartMentId()).get();
	   vet.update(requestDto.getName(),requestDto.getDescription(),dep);
	   return requestDto;
   }
   
   @Transactional
   public List<Vet> getByKeyword(String keyword){
	   return vetRepository.findByKeyword(keyword);
	   
   }
}
