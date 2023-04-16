package com.example.PetHospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.PetHospital.domain.Chart;

public interface ChartRepository extends JpaRepository<Chart, Long> {

}
