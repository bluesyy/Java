package com.test.kr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.kr.service.OpenApiService;

@Controller
public class controller {
	
	@Autowired
	private OpenApiService openApiService;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	
	@ResponseBody
	@GetMapping("/dailyBoxOffice")
	public String dailyBoxOffiec(@RequestParam String targetDt) {
		return openApiService.boxOffice(targetDt);
	}
	
	
	@GetMapping("/openapi")  // ${contextPath}/reservation 요청이 오면,
	public String openapi() {
		return "openapi";  // reservation.jsp로 이동하자.
	}
	
	
}
