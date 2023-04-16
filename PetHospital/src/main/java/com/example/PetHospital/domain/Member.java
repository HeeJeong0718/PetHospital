package com.example.PetHospital.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.example.PetHospital.dto.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class Member extends BaseTimeEntity {
   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String phone;
	
	@Setter
	@Embedded
	private Address address;
	
	@JsonIgnore
	@OneToMany(mappedBy ="Member" , fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Pet> pets;
	
	
	@Builder
	public Member(String name, String phone, Address address) {
		this.name = name;
		this.phone = phone;
		this.address = address;
	}
	public void update(String name, String phone, Address address) {
		this.name = name;
		this.phone = phone;
		this.address  = address;
	}
	
	
	
	@Override
	public String toString() {
		return "name" + this.getName() + "phone" + this.getPhone() + "address" + this.getAddress();
	}
}
