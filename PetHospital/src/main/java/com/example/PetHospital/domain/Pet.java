package com.example.PetHospital.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.Nullable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Pet extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String birthdate;
	@Column(name = "GenderType")
	@Enumerated(EnumType.STRING)
	private GenderType GenderType;

	private String imgName;
	private String imgUrl;
	private Long filesize;
	
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member Member;

	@JsonIgnore
	@Nullable
	@OneToOne(mappedBy = "pet")
	private Chart chart;
	

	@Builder
	public Pet(String name, String birthdate, Member Member, GenderType GenderType, Chart chart, String imgName, String imgUrl) {
		this.name = name;
		this.birthdate = birthdate;
		this.Member = Member;
		this.GenderType = GenderType;
		this.chart = chart;
		this.imgName = imgName;
		this.imgUrl = imgUrl;
	}

	public void update(String name, String birthdate, GenderType GenderType , String imgName, String imgUrl) {
		this.name = name;
		this.birthdate = birthdate;
		this.GenderType = GenderType;
		this.imgName = imgName;
		this.imgUrl = imgUrl;
	}
}
