package com.example.PetHospital.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.example.PetHospital.domain.Users;
import com.example.PetHospital.dto.TokenDto;
import com.example.PetHospital.exception.CRefreshTokenException;
import com.example.PetHospital.repository.UsersRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@RequiredArgsConstructor
@Component
@Slf4j
public class JwtProvider {
	//jwt생성 
	private String secretKey ="webPetHospital";
	private String ROLES = "roles";
	private final Long accessTokenValid = 600000L;
	private final Long refreshTokenValid = 1200000L;
	
	private final UserDetailsService userDetailsService;
	private final UsersRepository usersRepository;
  
	@PostConstruct
	protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
	}
	
	//1.jwt생성
	public TokenDto createToken(Long userPk, List<String> roles) {
		Claims claims = Jwts.claims().setSubject(String.valueOf(userPk));
		claims.put(ROLES, roles);
		Optional<Users> users = usersRepository.findById(userPk);
		//생성날짜 , 만료날짜를 위한 date
		Date now = new Date();
		String accessToken = Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime()+ accessTokenValid))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
		
		//리프레쉬 토큰 생성
		String refreshToken = Jwts.builder()
				.setExpiration(new Date(now.getTime()+ refreshTokenValid))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
		
		return TokenDto.builder()
				.grantType("Bearer")
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.accessTokenExpireDate(accessTokenValid)
				.refreshTokenExpireDate(refreshTokenValid)
				.id(userPk)
				.name(users.get().getName())
				.build();
	}
	
	//jwt토큰 복호화해서 가져오기
		private Claims parseClaims(String token) {
			try {
				return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
			}catch(ExpiredJwtException e) {
				return e.getClaims();
			}
		}
	//Jwt로 인증조회
	public Authentication getAuthentication(String token) {
		Claims claims = parseClaims(token);
		UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
		return new UsernamePasswordAuthenticationToken(userDetails,"", userDetails.getAuthorities());
	}
	//Header에서 Token parsing
	public String resolveToken(HttpServletRequest request) {
		return request.getHeader("X-AUTH-TOKEN");
	}
	public boolean isExpiredToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser()
					.setSigningKey(secretKey)
					.parseClaimsJws(token);
			return claims.getBody().getExpiration().before(new Date());
		}catch(Exception e) {
			return false;
		}
	}
	//jwt 유효성검사
	public boolean validationType(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		}catch(IllegalArgumentException e) {
			log.error("잘못된토큰입니다");
		}catch(CRefreshTokenException e) {
			log.error("CRefreshTokenException empty: {}", e.getMessage());
		}
		return false;
	}
}  
