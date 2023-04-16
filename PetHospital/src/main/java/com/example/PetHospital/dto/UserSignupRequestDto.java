package com.example.PetHospital.dto;

import java.util.Collections;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.PetHospital.domain.Users;

import groovy.transform.builder.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSignupRequestDto {
    
	private String loginId;
	private String name; 
	private String password;
	private String email;
	
	@Builder
	public UserSignupRequestDto(String loginId, String name , String password , String email) {
		this.loginId = loginId;
		this.name = name;
		this.password = password;
		this.email = email;
	}
	public Users ToEntity(PasswordEncoder passwordEncoder) {
		if(name.equals("admin")) {
			return Users.builder()
				    .loginId(loginId)
					.email(email)
					.password(passwordEncoder.encode(password))
					.name(name)
					.roles(Collections.singletonList("ROLE_ADMIN"))
					.build();
		}else {
			return Users.builder()
					 .loginId(loginId)
					.email(email)
					.password(passwordEncoder.encode(password))
					.name(name)
					.roles(Collections.singletonList("ROLE_USER"))
					.build();
		   }
			
		}
	
}
