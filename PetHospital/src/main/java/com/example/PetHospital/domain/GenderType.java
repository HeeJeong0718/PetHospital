package com.example.PetHospital.domain;

import lombok.Getter;


public enum GenderType {
	MALE("M"), FEMAIL("F");
	
private final String name; 
	
	private GenderType(String value) {
		name = value;
	}
	@Override
	public String toString() {
		return name;
	}

}
