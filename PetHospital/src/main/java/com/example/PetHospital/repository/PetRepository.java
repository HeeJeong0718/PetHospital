package com.example.PetHospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.PetHospital.domain.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {
  
	@Query(value = "select * from pet p where p.name like %:keyword% ",nativeQuery=true)
	List<Pet> findByKeyword (@Param("keyword") String keyword);
}
