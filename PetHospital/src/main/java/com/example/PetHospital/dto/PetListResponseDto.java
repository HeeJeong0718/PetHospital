package com.example.PetHospital.dto;

import com.example.PetHospital.domain.Chart;
import com.example.PetHospital.domain.GenderType;
import com.example.PetHospital.domain.Member;
import com.example.PetHospital.domain.Pet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor	// 기본 생성자 추가
@AllArgsConstructor
public class PetListResponseDto {
	private Long id;
	private String name;
	private String birthdate;
	private Member Member;
	private GenderType genderType;
	private Chart chart;
	private String imgName;
	private String imgUrl;
	private Long fileSize;
	
	
	
	public PetListResponseDto(Pet entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.birthdate = entity.getBirthdate();
		this.Member = entity.getMember();
		this.genderType = entity.getGenderType();
		this.chart = entity.getChart();
		this.imgName = entity.getImgName();
		this.imgUrl = entity.getImgUrl();
		this.fileSize = entity.getFilesize();
	}
}
