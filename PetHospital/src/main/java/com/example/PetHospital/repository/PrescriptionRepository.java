package com.example.PetHospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.PetHospital.domain.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

}
