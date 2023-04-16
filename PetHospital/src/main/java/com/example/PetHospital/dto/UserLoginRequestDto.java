package com.example.PetHospital.dto;


import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.PetHospital.domain.Users;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginRequestDto {
	
	private String email;
	private String  password;
	

	public Users toUser(PasswordEncoder passwordEncoder) {
	   return Users.builder()
			   .email(email)
			   .password(password)
			   .build();
	}

}
