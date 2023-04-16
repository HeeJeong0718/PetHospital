package com.example.PetHospital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.PetHospital.domain.RefreshToken;


public interface RefreshTokenRepository  extends JpaRepository<RefreshToken,Long>{
   
	Optional<RefreshToken> findByuserkey(Long userkey);
	
	Optional<RefreshToken> findById(Long id);
}
