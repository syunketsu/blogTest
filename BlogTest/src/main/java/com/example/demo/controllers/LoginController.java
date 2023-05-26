package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.services.AccountService;

@Controller
public class LoginController {
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/login")
	public String getLoginPage() {
		return "login.html";
	}
	
	@GetMapping("/homepage")
	public String getHomePage() {
		return "homepage.html";
	}
	
//	@PostMapping("/homepage")
//	public ModelAndView getUserHomePage(@RequestParam String userName,
//		@RequestParam String userPassword, ModelAndView mav) {
//		mav.addObject("name", userName);
//		mav.setViewName("homepage.html");
//		return mav;
//	}
	
	@PostMapping("/user-login")
	public ModelAndView getUserHomePage(@RequestParam String userName,
			@RequestParam String userPassword, ModelAndView mav) {
		if (accountService.validateAccount(userName, userPassword)) {
			mav.addObject("name", userName);
			mav.setViewName("homepage.html");
		} else {
			mav.addObject("error", true);
			mav.setViewName("login.html");
		}
		return mav;
	}

}


