package com.example.PetHospital.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.PetHospital.exception.CustomAccessDeniedHandler;
import com.example.PetHospital.exception.CustomAuthenticationEntryPoint;
import com.example.PetHospital.handler.AuthFailureHandler;
import com.example.PetHospital.handler.AuthSuccessHandler;
import com.example.PetHospital.jwt.JwtAuthenticationFilter;
import com.example.PetHospital.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;



@EnableWebSecurity
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SecurityConfig  {

    //private final UserDetailsServiceImpl userDetailsService;
    private final AuthSuccessHandler authSuccessHandler;
    private final AuthFailureHandler authFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService; 
	private final JwtProvider jwtProvider;
	  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	    private final CustomAccessDeniedHandler customAccessDeniedHandler;
	

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
	 // authenticationManager를 Bean 등록합니다.
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
           http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		        .and()
		        .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/","/v1/user/signup","/v1/user/signin","/v1/user/reissue").permitAll()
                .antMatchers(HttpMethod.GET, "/exception/**").permitAll()
                .antMatchers("/v1/member","/v1/chart","/v1/vet","/v1/dep","/v1/pet","/v1/pre").hasRole("ADMIN")
                .antMatchers("/v2/member/**","/v2/chart/**","/v2/vet/**","/v2/dep/**","/v2/pet/**","/v2/pre/**").authenticated()
                //.anyRequest().hasRole("ADMIN")8
                .and()
		        .exceptionHandling()
		        .authenticationEntryPoint(customAuthenticationEntryPoint)
		        .accessDeniedHandler(customAccessDeniedHandler)
		      
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/web/user/logout"))
                .logoutSuccessUrl("/web/main")
                .invalidateHttpSession(true)
                .deleteCookies("name").permitAll()
                .deleteCookies("accessToken").permitAll()
                .deleteCookies("refreshToken").permitAll()
			       .and()
			        .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
	                return http.build();
 
    }
}
