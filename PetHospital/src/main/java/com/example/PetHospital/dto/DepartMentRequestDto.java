package com.example.PetHospital.dto;

import com.example.PetHospital.domain.DepartMent;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class DepartMentRequestDto  {
	
	private String name;
	
	@Builder
	public DepartMentRequestDto(String name) {
		this.name = name;
	}
	
    public DepartMent toEntity() {
    	return DepartMent.builder()
    			.name(name)
    			.build();
    }
}
