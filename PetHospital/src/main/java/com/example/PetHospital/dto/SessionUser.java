package com.example.PetHospital.dto;

import java.io.Serializable;

import com.example.PetHospital.domain.Users;

import lombok.Getter;

/**
 * 직렬화 기능을 가진 User클래스
 */
@Getter
public class SessionUser implements Serializable {
    private String username;
    private String email;
    //private String picture;

    public SessionUser(Users user){
        this.username = user.getUsername();
        this.email = user.getEmail();
       // this.picture = user.getPicture();
    }
    
    @Override
	public String toString() {
		return "name" + this.getUsername();
	}
}
