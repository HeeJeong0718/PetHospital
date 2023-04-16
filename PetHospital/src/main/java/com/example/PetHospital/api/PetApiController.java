package com.example.PetHospital.api;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.PetHospital.dto.PetListResponseDto;
import com.example.PetHospital.dto.PetRequestDto;
import com.example.PetHospital.response.ListResult;
import com.example.PetHospital.response.ResponseService;
import com.example.PetHospital.response.SingleResult;
import com.example.PetHospital.service.PetService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v2/pet")
@RequiredArgsConstructor
public class PetApiController {
	
	private final PetService petService;
	private final ResponseService responseService;
	
	@PostMapping("/createPet/{id}")
	public SingleResult<PetRequestDto> savePet(@PathVariable Long id, PetRequestDto requestDto , @RequestParam("imgFile") MultipartFile multipartFile)throws Exception {
		PetRequestDto petId  = petService.save(id, requestDto , multipartFile);
		return responseService.getSingleResult(petId);
	}
	
	 @CrossOrigin(origins="*")
	@GetMapping("/ListPet")
	public ListResult<PetListResponseDto> findAll(@CookieValue(name = "accessToken", required = true) final String token){
		return responseService.getListResult(petService.findAll());
	}
	 @CrossOrigin(origins="*")
	@GetMapping("/pet/{id}")
   public SingleResult<PetListResponseDto> getPet(@PathVariable Long id){
		return responseService.getSingleResult(petService.findById(id));
	}
	
}
