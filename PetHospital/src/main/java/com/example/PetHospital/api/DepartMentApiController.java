package com.example.PetHospital.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PetHospital.dto.DepartMentRequestDto;
import com.example.PetHospital.response.ResponseService;
import com.example.PetHospital.response.SingleResult;
import com.example.PetHospital.service.DepartMentService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v2/dep")
@RequiredArgsConstructor
public class DepartMentApiController {
   
	private final DepartMentService departMentService;
	private final ResponseService responseService;
	
	 @CrossOrigin(origins="*")
	@PostMapping("/createDep")
	public SingleResult<DepartMentRequestDto> saveDep(@RequestBody DepartMentRequestDto requestDto){
		 DepartMentRequestDto result = departMentService.save(requestDto);
		return responseService.getSingleResult(result);
	};
}
