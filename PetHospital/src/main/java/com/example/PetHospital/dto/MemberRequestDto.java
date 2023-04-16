package com.example.PetHospital.dto;


import com.example.PetHospital.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {
	private String name;
	private String phone;
	private Address address; 
	private String detailAdr;
	private String street;
	private String zipcode;
   
	
	
	public Member toEntity() {
		return Member.builder()
				.name(name)
				.phone(phone)
				.address(address)
				.build();
	}
}
