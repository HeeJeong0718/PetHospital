package com.example.PetHospital.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListResult<T> extends CommonResult {
    //복수권처리
	private List<T> list;
	
}
