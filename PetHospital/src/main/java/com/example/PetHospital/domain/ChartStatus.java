package com.example.PetHospital.domain;

public enum ChartStatus {
	RECEIPT("RECEIPT"), PROGRESS("PROGRESS"), COMPLETE("COMPLETE");
	
private final String name; 
	
	private ChartStatus(String value) {
		name = value;
	}
	@Override
	public String toString() {
		return name;
	}

}
