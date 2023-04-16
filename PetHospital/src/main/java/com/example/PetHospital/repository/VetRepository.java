package com.example.PetHospital.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.PetHospital.domain.Pet;
import com.example.PetHospital.domain.Vet;

public interface VetRepository extends JpaRepository<Vet, Long> {
   
	  Vet findByName(String name);  
	
	  @Query(value = "select * from Vet v where v.name like %:keyword% ",nativeQuery=true)
		List<Vet> findByKeyword (@Param("keyword") String keyword);
}
