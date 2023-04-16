package com.example.PetHospital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.PetHospital.domain.Users;

public interface UsersRepository  extends JpaRepository<Users, Long>{

    Optional<Users> findByLoginId(String loginId);
    
    Optional<Users> findByName(String name);
    
	 Optional<Users> findByEmail(String email);
	
}
