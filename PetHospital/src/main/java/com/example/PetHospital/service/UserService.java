package com.example.PetHospital.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.PetHospital.domain.RefreshToken;
import com.example.PetHospital.domain.Users;
import com.example.PetHospital.dto.PetListResponseDto;
import com.example.PetHospital.dto.TokenDto;
import com.example.PetHospital.dto.TokenRequestDto;
import com.example.PetHospital.dto.UserLoginRequestDto;
import com.example.PetHospital.dto.UserResponseDto;
import com.example.PetHospital.dto.UserSignupRequestDto;
import com.example.PetHospital.exception.CEmailLoginFailedException;
import com.example.PetHospital.exception.CRefreshTokenException;
import com.example.PetHospital.jwt.JwtProvider;
import com.example.PetHospital.repository.RefreshTokenRepository;
import com.example.PetHospital.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
   
	private final UsersRepository usersRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final PasswordEncoder PasswordEncoder;
	private final JwtProvider jwtProvider;
	private final RefreshTokenRepository refreshTokenRepository;

	//리스트
		public List<UserResponseDto> findAll(){
			return usersRepository.findAll().stream()
					.map(UserResponseDto::new)
					.collect(Collectors.toList());
		}
	//회원가입
	@Transactional
	public UserSignupRequestDto join(UserSignupRequestDto requestDto) {
	  usersRepository.findByLoginId(requestDto.getLoginId())
	  .ifPresent(user -> {
		  throw new IllegalArgumentException("Faild: 이미 존재하는 ID 입니다");
	  });
	  usersRepository.save(requestDto.ToEntity(PasswordEncoder));
	  return requestDto;
	  
	}
	//로그인
	@Transactional
	public TokenDto login(UserLoginRequestDto userLoginRequestDto) {
		//1.회원정보 존재확인 - email로 조회
		Users user = usersRepository.findByEmail(userLoginRequestDto.getEmail())
            .orElseThrow(CEmailLoginFailedException::new);
		//회원패스워드 불일치 확인
		if(!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword()))
			throw new CEmailLoginFailedException("no user");
		//accssToken 발금 - >refreshToken 추가
		TokenDto tokenDto = jwtProvider.createToken(user.getUserId(), user.getRoles());
		
		RefreshToken refreshToken = RefreshToken.builder()
				.userkey(user.getUserId())
				.token(tokenDto.getRefreshToken())
				.build();
		
		refreshTokenRepository.save(refreshToken);
		return tokenDto;
	}
	//토큰재발급
	@Transactional
	public TokenDto reissue(TokenRequestDto tokenRequestDto) {
		//리프레쉬토큰 만료 기한 체크
		   // 리프레시 토큰 만료 기한 체크
			 String refreshToken2 = tokenRequestDto.getRefreshToken();
	        if (!jwtProvider.isExpiredToken(refreshToken2)) {
	        	System.out.println("TOKEN만료됨!!");
	        	throw new CRefreshTokenException("refreshToken was expired please sign again");
	        }
		String accessToken = tokenRequestDto.getAccessToken();
		Authentication authentication = jwtProvider.getAuthentication(accessToken);
		//userPk로 검색
		Users user = usersRepository.findById(Long.parseLong(authentication.getName()))
				.orElseThrow(CRefreshTokenException::new);
		RefreshToken refreshToken = refreshTokenRepository.findByuserkey(user.getUserId())
				.orElseThrow(CRefreshTokenException::new);
		//리프레시 토큰 불일치에러
		if(!refreshToken.getToken().equals(tokenRequestDto.getRefreshToken()))
			   throw new CRefreshTokenException("Refresh token was expired. Please make a new signin request");
	   //accssToken , refreshToken 토큰재발급, 리프레시토큰다시저장
		TokenDto newCreatedToken = jwtProvider.createToken(user.getUserId(), user.getRoles());
		RefreshToken upRefreshToken = refreshToken.updateToken(newCreatedToken.getRefreshToken());
		
		refreshTokenRepository.save(upRefreshToken);
		
		return newCreatedToken;
	}
} 
