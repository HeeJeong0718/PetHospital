package com.example.PetHospital.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.PetHospital.domain.Member;

public interface MemberRepository  extends JpaRepository<Member, Long>{
  
	@Query(value = "select * from Member m where m.name like %:keyword% ",nativeQuery=true)
	List<Member> findByKeyword (@Param("keyword") String keyword);
	
	Optional<Member> findByName(String name);
	
}
