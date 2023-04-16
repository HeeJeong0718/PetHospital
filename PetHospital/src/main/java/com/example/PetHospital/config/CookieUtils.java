package com.example.PetHospital.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.example.PetHospital.dto.TokenDto;

public class CookieUtils {
   
	public static void createCookieForJwt(TokenDto tokenDto , HttpServletResponse response) {
		Cookie accessCookie = new Cookie("accessToken", tokenDto.getAccessToken());
		Cookie refreshCookie = new Cookie("refreshToken", tokenDto.getRefreshToken());
		Cookie name = new Cookie("name" , tokenDto.getName());
		
		accessCookie.setPath("/");
        accessCookie.setMaxAge(24*60*60); 
        accessCookie.setHttpOnly(true);

        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(600);
        refreshCookie.setHttpOnly(true);

        name.setPath("/");
        name.setMaxAge(600);
        name.setHttpOnly(true);
        
        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
        response.addCookie(name);
		
		
	}
}
