package com.example.PetHospital.dto;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable
@Getter
public class Address {
   
	
	private String detailAdr;
	private String street;
	private String zipcode;
   
     protected Address() {
		 
		
	}
	public Address(String detailAdr, String street, String zipcode) {
		this.detailAdr = detailAdr;
		this.street = street;
		this.zipcode = zipcode;
	}
}
