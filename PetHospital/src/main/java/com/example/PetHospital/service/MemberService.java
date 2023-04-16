package com.example.PetHospital.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.PetHospital.domain.Member;
import com.example.PetHospital.dto.Address;
import com.example.PetHospital.dto.MemberRequestDto;
import com.example.PetHospital.dto.MemberResponseDto;
import com.example.PetHospital.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
   
	private final MemberRepository memberRepository;
	
	//1.저장하기
	@Transactional
	public MemberRequestDto usersave(MemberRequestDto memberRequestDto) {
		//회원체크
		memberRepository.findByName(memberRequestDto.getName())
		.ifPresent(member -> {
			throw new IllegalArgumentException("Faild: 이미 존재하는 회원입니다"); 
		});
        Address address = new  Address(memberRequestDto.getZipcode(),memberRequestDto.getStreet(),memberRequestDto.getDetailAdr());
		System.out.println("addr::" + address);
        //Member member = new Member();
		memberRequestDto.setAddress(address);
		memberRepository.save(memberRequestDto.toEntity());
		return memberRequestDto;
	}
	
	
	public List<MemberResponseDto> findAll() {
		return memberRepository.findAll().stream()
				.map(MemberResponseDto::new)
				.collect(Collectors.toList());

	}
	
	public MemberResponseDto findById(Long id) {
		Member mem = memberRepository.findById(id).get();
	   return new MemberResponseDto(mem);	
	}
	@Transactional
	public MemberRequestDto modify(Long id, MemberRequestDto requestDto) {
		Member mem = memberRepository.findById(id).get();
		  Address address = new  Address(requestDto.getZipcode(),requestDto.getStreet(),requestDto.getDetailAdr());
		  requestDto.setAddress(address);
		mem.update(requestDto.getName(), requestDto.getPhone(), requestDto.getAddress());
		return requestDto;
	}
	@Transactional
	public List<Member> getByKeywor(String keyword){
		return memberRepository.findByKeyword(keyword);
	}
	@Transactional
	public void delete(Long id) {
		memberRepository.deleteById(id);	
	}
	
} 
