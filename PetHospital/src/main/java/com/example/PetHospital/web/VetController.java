package com.example.PetHospital.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PetHospital.dto.VetRequestDto;
import com.example.PetHospital.dto.VetResponseDto;
import com.example.PetHospital.dto.VetUpdateResponseDto;
import com.example.PetHospital.response.ListResult;
import com.example.PetHospital.response.ResponseService;
import com.example.PetHospital.response.SingleResult;
import com.example.PetHospital.service.VetService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/vet")
@RequiredArgsConstructor
public class VetController {
   
	private final VetService vetService;
	private final ResponseService responseService;
	
	@PostMapping("/createVet/{depId}")
	public SingleResult<VetRequestDto> saveVet(@PathVariable Long depId, @RequestBody VetRequestDto requestDto){
		VetRequestDto  vetId = vetService.saveVet(depId, requestDto);
		return responseService.getSingleResult(vetId);
	}
	@GetMapping("/ListVet")
	public ListResult<VetResponseDto> findAll(){
		return responseService.getListResult(vetService.findAll());
	}
	@GetMapping("/vet/{id}")
	public SingleResult<VetResponseDto> findById(@PathVariable Long id){
		return responseService.getSingleResult(vetService.findById(id));
	}
	
	@PutMapping("/modify/{vetId}")
	public SingleResult<VetResponseDto> update(@PathVariable Long vetId,  @RequestBody VetResponseDto requestDto){
		VetResponseDto dto = vetService.modifyVet(vetId, requestDto);
		return responseService.getSingleResult(dto);
	}
}
