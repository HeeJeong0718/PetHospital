package com.example.PetHospital.api;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PetHospital.dto.MemberRequestDto;
import com.example.PetHospital.dto.MemberResponseDto;
import com.example.PetHospital.dto.PetListResponseDto;
import com.example.PetHospital.response.CommonResult;
import com.example.PetHospital.response.ListResult;
import com.example.PetHospital.response.ResponseService;
import com.example.PetHospital.response.SingleResult;
import com.example.PetHospital.service.MemberService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v2/member")
@RequiredArgsConstructor
public class MemberApiController {
   
	private final MemberService memberService;
	private final ResponseService responseService;

	
	 @CrossOrigin(origins="*")
		@PostMapping("/createMember") //입력자료형이 Long 이니까 service에서 return getUserId를 가쟈온다
		public SingleResult<MemberRequestDto> singup(@RequestBody MemberRequestDto memberRequestDto){
			  MemberRequestDto signupId = memberService.usersave(memberRequestDto);
			  return responseService.getSingleResult(signupId); 
			  //api결과 방식 return data : 2
		 }
	 @CrossOrigin(origins="*")
	 @GetMapping("/ListMember")
		public ListResult<MemberResponseDto> findAll(){
			return responseService.getListResult(memberService.findAll());
		}
	 @CrossOrigin(origins="*")
	@GetMapping("/mem/{id}")
	  public SingleResult<MemberResponseDto> findById(@PathVariable Long id){
		return responseService.getSingleResult(memberService.findById(id));
	}
	 @CrossOrigin(origins="*")
	@PutMapping("/modify/{id}")
	 public SingleResult<MemberRequestDto> modify(@PathVariable Long id, @RequestBody MemberRequestDto requestDto){
		return responseService.getSingleResult(memberService.modify(id, requestDto));
	}
	 @CrossOrigin(origins="*")
	@DeleteMapping("/delete/{id}")
	public CommonResult delete(@CookieValue(name = "accessToken", required = true)@PathVariable Long id) {
	   memberService.delete(id);
	   return responseService.getSuccessResult();
	}

	
}
