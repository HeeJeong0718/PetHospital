package com.example.PetHospital.dto;

import com.example.PetHospital.domain.GenderType;
import com.example.PetHospital.domain.Member;
import com.example.PetHospital.domain.Pet;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PetRequestDto {
    private Long memberid;
	private String name;
	private String birthdate;
	private Member Member;
	private GenderType GenderType;
	private String imgName;
	private String imgUrl;
	private Long fileSize;
	
	@Builder
	public PetRequestDto(Long id, Long memberid, String name, String birthdate, Member Member , GenderType GenderType , String imgName, String imgUrl) {
	
		this.memberid = memberid;
		this.name = name;
		this.birthdate = birthdate;
		this.Member = Member;
		this.GenderType = GenderType;
		this.imgName = imgName;
		this.imgUrl = imgUrl;
	}
	public Pet toEntity() {
		return Pet.builder()
				.name(name)
				.birthdate(birthdate)
				.Member(Member)
				.GenderType(GenderType)
				.imgName(imgName)
				.imgUrl(imgUrl)
				.build();
	}
}
