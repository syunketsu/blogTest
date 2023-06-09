package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.services.AccountService;

@Controller
public class RegisterController {
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/register")
	public String getRegisterPage() {
		return "register.html";
	}
	
	@PostMapping("/register")
	public ModelAndView register(@RequestParam String userName,
			@RequestParam String userPassword, ModelAndView mav) {
		if (accountService.createAccount(userName, userPassword)) {
			mav.addObject("error", false);
			mav.setViewName("login.html");
		} else {
			mav.addObject("error", true);
			mav.setViewName("register.html");
		}
		return mav;
	}
	
//	@PostMapping("/register")
//	public String register(@RequestParam String userName,@RequestParam String userPassword) {
//		//もし保存をした場合には、login.htmlへ遷移する
//		if (accountService.createAccount(userName, userPassword)) {
//			return "redirect:/login";
//		} else {
//			//そうでない場合には、register.htmlに遷移する
//			return "register.html";
//		}
//	}
}

