package com.example.PetHospital.dto;

import com.example.PetHospital.domain.DepartMent;
import com.example.PetHospital.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor	// 기본 생성자 추가
@AllArgsConstructor
public class MemberResponseDto {
	private Long id;
	private String name;
	private String phone;
	private Address address; 
	private String detailAdr;
	private String street;
	private String zipcode;
   
	
	public MemberResponseDto(Member entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.phone = entity.getPhone();
		this.address = entity.getAddress();
		
	}
}
