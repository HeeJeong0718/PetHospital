package com.example.PetHospital.dto;

import com.example.PetHospital.domain.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor	// 기본 생성자 추가
@AllArgsConstructor
public class UserResponseDto {
  
	private Long userId;
	 private String loginId;
	private String password;
	private String email;
	private String  name;
	
	public UserResponseDto(Users entity) {
		this.userId = entity.getUserId();
		this.loginId = entity.getLoginId();
		this.password = entity.getPassword();
		this.email = entity.getEmail();
		this.name = entity.getName();
	}
}
