package com.example.PetHospital.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PetHospital.dto.ChartRequestDto;
import com.example.PetHospital.dto.ChartResponseDto;
import com.example.PetHospital.response.ResponseService;
import com.example.PetHospital.response.SingleResult;
import com.example.PetHospital.service.ChartService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/chart")
@RequiredArgsConstructor
public class ChartController {
   private final ChartService chartService;
   private final ResponseService responseService;
   
   @PostMapping("/createChart/{petId}/{vetId}")
   public SingleResult<ChartRequestDto> saveChart(@PathVariable Long petId, @PathVariable Long vetId, @RequestBody ChartRequestDto requestDto){
	   ChartRequestDto chartId = chartService.save(petId, vetId, requestDto);
	   return responseService.getSingleResult(chartId);
   }
   
   @GetMapping("/chart/{id}")
   public SingleResult<ChartResponseDto> findById(@PathVariable Long id){
	  return responseService.getSingleResult(chartService.findById(id));
   }
   @PutMapping("/update/{id}")
   public SingleResult<ChartResponseDto> modify(@PathVariable Long id, @RequestBody ChartResponseDto requestDto){
	   ChartResponseDto dto = chartService.modify(id, requestDto);
	   return responseService.getSingleResult(dto);
   }
     
}
