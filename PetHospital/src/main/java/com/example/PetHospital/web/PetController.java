package com.example.PetHospital.web;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.PetHospital.domain.Pet;
import com.example.PetHospital.dto.PetListResponseDto;
import com.example.PetHospital.dto.PetRequestDto;
import com.example.PetHospital.response.CommonResult;
import com.example.PetHospital.response.ListResult;
import com.example.PetHospital.response.ResponseService;
import com.example.PetHospital.response.SingleResult;
import com.example.PetHospital.service.PetService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/pet")
@RequiredArgsConstructor
public class PetController {
	
	private final PetService petService;
	private final ResponseService responseService;
	
	@PostMapping("/createPet/{id}")
	public SingleResult<PetRequestDto> savePet(@PathVariable Long id, PetRequestDto requestDto , @RequestParam("imgFile") MultipartFile multipartFile)throws Exception {
		PetRequestDto petId  = petService.save(id, requestDto , multipartFile);
		return responseService.getSingleResult(petId);
	}
	
	@GetMapping("/ListPet")
	public ListResult<PetListResponseDto> findAll(){
		return responseService.getListResult(petService.findAll());
	}
	@GetMapping("/pet/{id}")
   public SingleResult<PetListResponseDto> getPet(@PathVariable Long id){
		return responseService.getSingleResult(petService.findById(id));
	}
	@PostMapping("/modify/{id}")
	public SingleResult<PetRequestDto> modifyPet(@PathVariable Long id,  PetRequestDto petRequestDto ,@RequestParam("imgFile") MultipartFile multipartFile)throws Exception {
		return responseService.getSingleResult(petService.modify(id, petRequestDto,multipartFile));
	}
	@DeleteMapping("/delete/{id}")
	public CommonResult delete(@PathVariable Long id) {
		petService.delete(id);
		return responseService.getSuccessResult();
	}
}
