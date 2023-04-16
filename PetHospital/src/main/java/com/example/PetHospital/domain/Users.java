package com.example.PetHospital.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Users extends BaseTimeEntity implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	 private String loginId;
	private String password;
	private String email;
	private String  name; 
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	
	public Users update(String name) {
		this.name = name;
		return this;
	}
	
	/*@Builder
	public Users(String loginId, String username, String password , String email , Role role) {
		this.loginId = loginId;
		this.username  = username;
		this.password = password;
		this.email = email;
		this.role = role;
	}*/
	public String getRoleKey() {
		return this.role.getKey();
	}

	 @ElementCollection(fetch = FetchType.EAGER)
	    @Builder.Default
	    private List<String> roles = new ArrayList<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

	}
	   @Override
	    public String getPassword() {
	        return this.password;
	    }
	 //security에서 사용하는 회원구분 id
		@Override
		public String getUsername() {
			  return String.valueOf(this.userId);
		}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
