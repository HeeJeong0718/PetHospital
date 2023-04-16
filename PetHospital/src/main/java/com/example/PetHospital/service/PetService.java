package com.example.PetHospital.service;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.PetHospital.domain.Member;
import com.example.PetHospital.domain.Pet;
import com.example.PetHospital.dto.PetListResponseDto;
import com.example.PetHospital.dto.PetRequestDto;
import com.example.PetHospital.repository.MemberRepository;
import com.example.PetHospital.repository.PetRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class PetService {
   
	private final PetRepository petRepository;
	private final MemberRepository memberRepository;
	
	//저장
	@Transactional
	public PetRequestDto save(Long id, PetRequestDto requestDto, MultipartFile file) throws Exception{
		Member mem = memberRepository.findById(id).get();
		requestDto.setMember(mem);
		
		String projectpath = System.getProperty("user.dir") + "/src/main/resources/static/files";
	    
		UUID uuid = UUID.randomUUID();
		String fileName = file.getOriginalFilename();
		File saveFile = new File(projectpath, fileName);
		
		file.transferTo(saveFile);
		
		requestDto.setImgName(fileName);
        requestDto.setImgUrl("/files/" + fileName);		
		
		petRepository.save(requestDto.toEntity());
		return requestDto;
	}
	public List<PetListResponseDto> findAll(){
		return petRepository.findAll().stream()
				.map(PetListResponseDto::new)
				.collect(Collectors.toList());
	}

	public PetListResponseDto findById(Long id) {
		Pet pet = petRepository.findById(id).get();
		return new PetListResponseDto(pet);
	}
	@Transactional
	public List<Pet> getByKeyword(String keyword){
		return petRepository.findByKeyword(keyword);
	}
	@Transactional
	public PetRequestDto modify(Long id, PetRequestDto requestDto, MultipartFile file) throws Exception{
		Pet pet = petRepository.findById(id).get();
       
		if(file.getOriginalFilename().equals("")) {
			System.out.println("file이 있으면::" + file.getOriginalFilename());
			requestDto.setImgName(requestDto.getImgName());
			requestDto.setImgUrl(requestDto.getImgUrl());
		}else if(file.getOriginalFilename() != null){
			//새 파일이 있으면
			System.out.println("file::" + file.getOriginalFilename());
			File f = new File(pet.getImgUrl());
			System.out.println("f::" + f);
			if(f.exists()) {
				f.delete();
			}
		}
		
		String projectpath = System.getProperty("user.dir") + "/src/main/resources/static/files";
		UUID uuid = UUID.randomUUID();
		String fileName = file.getOriginalFilename();
		String fileName2 = pet.getImgName();
		File saveFile = new File(projectpath, fileName);
		 file.transferTo(saveFile);
		System.out.println("fileName::" + fileName);
		System.out.println("saveFile::" + saveFile);
        requestDto.setImgName(fileName);
        requestDto.setImgUrl("/files/" + fileName);		
		pet.update(requestDto.getName(), requestDto.getBirthdate(),requestDto.getGenderType(),requestDto.getImgName(),requestDto.getImgUrl());
		return requestDto;
		
	}
	@Transactional
	public void delete(Long id) {
		petRepository.deleteById(id);
	}
	
}
