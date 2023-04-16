package com.example.PetHospital.api;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PetHospital.config.CookieUtils;
import com.example.PetHospital.dto.TokenDto;
import com.example.PetHospital.dto.TokenRequestDto;
import com.example.PetHospital.dto.UserLoginRequestDto;
import com.example.PetHospital.dto.UserSignupRequestDto;
import com.example.PetHospital.response.ResponseService;
import com.example.PetHospital.response.SingleResult;
import com.example.PetHospital.service.UserService;

import lombok.RequiredArgsConstructor;
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequiredArgsConstructor
@RequestMapping("/v2/user")
@RestController
public class UserApiController {
  
	private final UserService userService;
	private final ResponseService responseService;
	
	 @CrossOrigin(origins="*")
	@PostMapping("/signup")
	public SingleResult<UserSignupRequestDto> signup(@RequestBody UserSignupRequestDto requestDto){
		UserSignupRequestDto result = userService.join(requestDto);
		return responseService.getSingleResult(result);
	}
	//jwt로 로그인 api
	 @CrossOrigin(origins="*")
	@PostMapping("/signin")
	public SingleResult<TokenDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto ,  HttpServletResponse response ){
		TokenDto tokenDto = userService.login(userLoginRequestDto);
		CookieUtils.createCookieForJwt(tokenDto, response);
		return responseService.getSingleResult(tokenDto);
	}
	
	//refreshToken발급
	 @CrossOrigin(origins="*")
	 @PostMapping("/reissue")
	 public SingleResult<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto){
		 return responseService.getSingleResult(userService.reissue(tokenRequestDto));
	 }
}
