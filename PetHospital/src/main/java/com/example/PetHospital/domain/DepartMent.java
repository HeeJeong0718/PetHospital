package com.example.PetHospital.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class DepartMent extends BaseTimeEntity {
   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy ="departMent" , fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Vet> vets;
	
    @Builder
    public DepartMent(String name) {
    	this.name = name;
    }

}
