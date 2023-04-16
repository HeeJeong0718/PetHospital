# - Springboot 동물병원진료관리프로그램
  - SpringBoot를 활용한 동물병원진료관리 프로그램입니다. 회원과 펫의 정보를 입력하여 진료 현황과 수의사 등록이 가능하고 차트와 처방전을 발행이 가능합니다
# 개발 기간 &참여인원
 - 2023년 3월~2023년4월
 - 개인 프로젝트


## 1.사용한 기술 및 툴
- <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=Spring&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Boot Jpa-6DB33F?style=flat-square&logo=Spring&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat-square&logo=Spring&logoColor=white"/>
- <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/>
- <img src="https://img.shields.io/badge/java-007396?style=flat-square&logo=java&logoColor=white"/>
- <img src="https://img.shields.io/badge/jQuery-0769AD?style=flat-square&logo=jQuery&logoColor=white"/>
- <img src="https://img.shields.io/badge/React-61DAFB?style=flat-square&logo=React&logoColor=black"/>
- <img src="https://img.shields.io/badge/Thymeleaf-3776AB?style=flat-square&logo=Python&logoColor=white"/>
- <img src="https://img.shields.io/badge/Postman-FF6C37?style=flat-square&logo=Postman&logoColor=white"/>
- <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=javascript&logoColor=black"/>


## 2.REST API 기능목록
- 회원가입 기능
- 로그인, 로그아웃 기능
- 토큰 발급 및 검증기능
- 회원등록,수정,삭제 (카카오주소api)
- 펫 등록,수정,삭제,파일 업로드
- 수의사 등록,수정,삭제
- 차트 등록,수정,삭제
-처방전 등록,수정,삭제

## 메인화면
  ![login](https://user-images.githubusercontent.com/104083733/232207225-b8bb77ad-6e56-4da2-9af7-0c8937d39240.png)
  ![스크린샷 2023-04-15 184020](https://user-images.githubusercontent.com/104083733/232207255-9d06950a-6da6-4887-9737-eb85a1aa86f5.png)
  ![스크린샷 2023-04-15 183828](https://user-images.githubusercontent.com/104083733/232207360-978d4827-64de-4f87-bdd3-0576a5e7d3f0.png)


 
## 3.클래스 다이어그램 및 테이블구조 
- ![image](https://user-images.githubusercontent.com/104083733/232207517-86b0e7fc-ce94-4a36-b0c0-ebc7adcd8d8c.png)



## 4.Spring security와 JWT연계하여 인증기능구현
- 토큰 기반의 인증 방식중 JWT(Json Web Token)을 이용하여 사용자 인증 처리를 구현하였습니다. 
- refreshToken 재발급 Axios 인터셉터 활용

## 5.refreshToken 재발급과 Axios 인터셉터 활용
- 인터셉터는 http 요청 및 응답이 실제로 전송되거나 수신되기 전에 필터로 이해할 수 있습니다
- Axios 인터셉터의 도움으로 accessToken(JWT)가 만료되었는지 확인하고 ,/reissue 로 새로운 수신 요청을 보내 accssToken을 다시 재발급 받을수 있습니다 
- ![image](https://user-images.githubusercontent.com/104083733/230705647-a8f97ef7-ade3-4f50-b6a1-f9efe75c5628.png)

- ![image](https://user-images.githubusercontent.com/104083733/230706577-1651b8f8-9f63-42a9-92f9-ba9f6fa955ab.png)

## 6.JWT 토큰 인증 시나리오
보호된 리소스에 접근하기 위하여 AccessToken과 RefreshToken발급 과정입니다

 1.클라이언트에서 ADMIN 권한이 필요한 리소스에 접근하기 위하여 인증요청(로그인 기반)
 
 2.AccessToken, RefreshToken을 발급하여 클라이언트에 전달
 
 3.보호된 리소스에 접근하는 경우 http 헤더에 jwt 추가 
 
 4.인증 완료 후 해당 리소스에 대해서 권한체크(ADMIN)
 
 5.AccssToken이 유효기간이 만료가 되면 token을 재발급하는 api를 호출하여 새로운 AccessToken과 RefreshToken을 재발급받음

## 7.JWT필터
JwtAuthenticationFilter는 실제로 JwtProvider를 사용하여 AccessToken과 refreshToken을 발급해주는 필터 클래스입니다
SpringSecurity에서 제공하고 있는 Filterchain 중 로그인 폼 기반의 UserNameAndPasswordFilter클래스보다 먼저 동작하여 이곳에서 인증이 
이루어지고 UserNameAndPasswordToken객체를 생성하여 UserNameAndPasswordFilter에게 인가 체크를 하게 합니다 
![image](https://user-images.githubusercontent.com/104083733/230706105-b3e7141f-a03e-4deb-a6a6-3df377f5103d.png)

## 8.Jwt 토큰 발급 및 검증 클래스
```
public class JwtProvider {
	//jwt생성 
	private String secretKey ="webPetHospital";
	private String ROLES = "roles";
	private final Long accessTokenValid = 60000L;
	private final Long refreshTokenValid = 120000L;
	
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
```
