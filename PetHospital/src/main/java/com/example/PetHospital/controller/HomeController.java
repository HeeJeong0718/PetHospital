package com.example.PetHospital.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.PetHospital.domain.Chart;
import com.example.PetHospital.domain.DepartMent;
import com.example.PetHospital.domain.Member;
import com.example.PetHospital.domain.Pet;
import com.example.PetHospital.domain.Prescription;
import com.example.PetHospital.domain.Users;
import com.example.PetHospital.domain.Vet;
import com.example.PetHospital.dto.ChartResponseDto;
import com.example.PetHospital.dto.MemberResponseDto;
import com.example.PetHospital.dto.PetListResponseDto;
import com.example.PetHospital.dto.PetRequestDto;
import com.example.PetHospital.dto.PrescriptionResponseDto;
import com.example.PetHospital.dto.SessionUser;
import com.example.PetHospital.dto.UserResponseDto;
import com.example.PetHospital.dto.VetResponseDto;
import com.example.PetHospital.repository.ChartRepository;
import com.example.PetHospital.repository.DepartMentRepository;
import com.example.PetHospital.repository.MemberRepository;
import com.example.PetHospital.repository.PetRepository;
import com.example.PetHospital.repository.PrescriptionRepository;
import com.example.PetHospital.repository.VetRepository;
import com.example.PetHospital.response.SingleResult;
import com.example.PetHospital.service.ChartService;
import com.example.PetHospital.service.MemberService;
import com.example.PetHospital.service.PetService;
import com.example.PetHospital.service.PrescriptionService;
import com.example.PetHospital.service.UserService;
import com.example.PetHospital.service.VetService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value="/web")
public class HomeController {
   
  	private final HttpSession httpSession;
	private final MemberService memberService;
	private final MemberRepository MemberRepository;
	private final DepartMentRepository departMentRepository;
	private final VetRepository vetRepository;
	private final PetRepository petRepository;
	private final ChartRepository chartRepository;
	private final PrescriptionRepository prescriptionRepository;
	private final PetService petService;
	private final ChartService chartService;
	private final VetService vetService;
	private final PrescriptionService preService;
	private final UserService userService;
	
	
	@GetMapping("/main")
	public String main( @CookieValue(name="name", required=false)String name,Model model ,@AuthenticationPrincipal Users user) {
		if(name != null) {
			model.addAttribute("name", name);	 
		}
		return "Main";
	}
	
	@GetMapping("/index")
	public String index( @CookieValue(name="name", required=false)String name,Model model ,@AuthenticationPrincipal Users user) {
		if(name != null) {
			model.addAttribute("name", name);	 
		}
		return "index";
	}
	
	//유저가입페이지
	@GetMapping("/signup")
	public String signup(@CookieValue(name="accessToken" , required=false) String token,  Model model, @RequestParam(value="msg", required=false) String msg) {
	    model.addAttribute("msg", msg);
	    log.info("token: ", token);
		return  "User/signup";
	}
	//로그링페이지
	@GetMapping("/login")
	public String login() {
		return "User/sign_in";
	}
	//유저로그아웃
	@GetMapping("/user/loguout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/";
	}
	
	//회원가입 페이지
	@GetMapping("/members/new")
	public String createMember() {
		  // log.info("token: ", token);
		return "Member/member_form";
	}
	@GetMapping("/memberList")
	public String MemberList(Model model , String keyword) {
		if(keyword != null) {
		 List<Member> mem = memberService.getByKeywor(keyword);
		 model.addAttribute("mem" ,mem);
		}else {
			List<Member> mem = MemberRepository.findAll();
			model.addAttribute("mem", mem);	
		}
		return "Member/member_list";
	}
	
	@GetMapping("/mem/update/{id}")
    public String memupdate(@PathVariable Long id, Model model) {
    	//한개를 가져오는 hotelResponseDto 생성
		MemberResponseDto mem = memberService.findById(id);
    	model.addAttribute("mem", mem);
    	return "Member/mem_update";
    }
	
	
	
	@GetMapping("/pet")
	public String createPet(Model model) {
		List<Member> members = MemberRepository.findAll();
		model.addAttribute("members", members);
		model.addAttribute("pet", new Pet());
		System.out.println("members::" + members.toString());
		return "Pet/Pet_form";
	}
	
	
	 @GetMapping("/search")
	public String PetList(Pet pet, Model model, String keyword) {
	       if(keyword != null) {
	    	 List<Pet> pets = petService.getByKeyword(keyword);
	 		  model.addAttribute("pets", pets);
	        }else {
	        	List<Pet> pets = petRepository.findAll();
				model.addAttribute("pets", pets); 	
	        }
		return "Pet/Pet_List";
	}
	

	@GetMapping("/search2")
	public String getKeyword(Pet pet, Model model, String keyword){
		List<Pet> list = petService.getByKeyword(keyword);
		model.addAttribute("list", list);
		return "Member/Pet_List";
	}
	
	@GetMapping("/pet/update/{id}")
    public String petupdate(@PathVariable Long id, Model model) {
		PetListResponseDto pet = petService.findById(id);
    	model.addAttribute("pet", pet);
    	return "Pet/pet_update";
    }
	
	
	
	@GetMapping("/vet")
	public String createVet(Model model) {
		List<DepartMent> dep = departMentRepository.findAll();
		model.addAttribute("departments", dep);
		return "Vet/Vet_form";
	}
	
	@GetMapping("/searchVet")
	public String vetList(Vet vet , Model model , String keyword) {
		if(keyword != null) {
			List<Vet> vets = vetService.getByKeyword(keyword);
			model.addAttribute("vet", vets);
		}else {
			List<Vet> vets = vetRepository.findAll();
			model.addAttribute("vet", vets);	
		}
		return "Vet/vet_list";
	}
	@GetMapping("/vet/update/{id}")
    public String vetupdate(@PathVariable Long id, Model model) {
    	//한개를 가져오는 hotelResponseDto 생성
		List<DepartMent> dep = departMentRepository.findAll();
		VetResponseDto vet = vetService.findById(id);
    	model.addAttribute("vet", vet);
    	model.addAttribute("dep", dep);
    	return "Vet/vet_update";
    }
	
	@GetMapping("/dep")
	public String createDep() {
		return "DepartMent/dep_form";
	}
	@GetMapping("/chart")
	public String createChart(Model model) {
		List<Vet> vet = vetRepository.findAll();
		List<Pet> pet = petRepository.findAll();
		model.addAttribute("vet", vet);
		model.addAttribute("pet", pet);
		return "Chart/chart_form";
	}
	@GetMapping("/chartList")
	public String chartList(Model model) {
		List<Chart> charts = chartRepository.findAll();
		model.addAttribute("charts", charts);
		return "Chart/chart_List";
	}
	@GetMapping("/chart/update/{id}")
    public String chartupdate(@PathVariable Long id, Model model) {
    	//한개를 가져오는 hotelResponseDto 생성
		ChartResponseDto chart = chartService.findById(id);
		List<Vet> vet = vetRepository.findAll();
    	model.addAttribute("chart", chart);
    	model.addAttribute("vet", vet);
    	return "Chart/chart_update";
    }
	
	
	@GetMapping("/pre")
	public String createPrescription(Model model) {
		List<Vet> vet = vetRepository.findAll();
		List<Chart> chart = chartRepository.findAll();
		model.addAttribute("vet", vet);
		model.addAttribute("chart", chart);
		return "Prescription/pre_form";
	}
	@GetMapping("/preList")
	public String preList(Model model) {
		List<Prescription> pre = prescriptionRepository.findAll();
		model.addAttribute("pre", pre);
		return "Prescription/pre_list";
	}
	@GetMapping("/pre/update/{id}")
	public String preupdate(@PathVariable Long id , Model model) {
		PrescriptionResponseDto pre = preService.findById(id);
		model.addAttribute("pre", pre);
		return "Prescription/pre_update";
	}
}
