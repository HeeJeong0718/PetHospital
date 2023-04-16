package com.example.PetHospital.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PetHospital.dto.PrescriptionRequestDto;
import com.example.PetHospital.dto.PrescriptionResponseDto;
import com.example.PetHospital.response.CommonResult;
import com.example.PetHospital.response.ListResult;
import com.example.PetHospital.response.ResponseService;
import com.example.PetHospital.response.SingleResult;
import com.example.PetHospital.service.PrescriptionService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/pre")
@RequiredArgsConstructor
public class PrescriptionController {
   
	private final PrescriptionService prescriptionService;
	private final ResponseService responseService;
	
	@GetMapping("/ListPre")
	public ListResult<PrescriptionResponseDto> findAll(){
		return responseService.getListResult(prescriptionService.findAll());
	}
	
	
	@PostMapping("/createPre/{vetId}/{chartId}")
	public SingleResult<PrescriptionRequestDto> savePre(@PathVariable Long vetId, @PathVariable Long chartId , @RequestBody PrescriptionRequestDto requestDto){
		PrescriptionRequestDto preId = prescriptionService.save(vetId, chartId, requestDto);
		return responseService.getSingleResult(preId);
	}
	
	@GetMapping("/pre/{id}")
	public SingleResult<PrescriptionResponseDto> getPre(@PathVariable Long id){
		return responseService.getSingleResult(prescriptionService.findById(id));
	}
	@DeleteMapping("/delete/{id}")
	public CommonResult delete(@PathVariable Long id) {
		prescriptionService.delete(id);
		return responseService.getSuccessResult();
		
		
	}
	
}
