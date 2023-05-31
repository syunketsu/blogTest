package com.example.demo.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.models.Account;
import com.example.demo.models.Blog;
import com.example.demo.services.AccountService;
import com.example.demo.services.BlogService;

@Controller
public class LoginController {
	
	@Autowired
	BlogService blogService;
	
	@GetMapping("/login")
	public String getLoginPage() {
		return "login.html";
	}
	
	@GetMapping("/homepage")
	public String getHomePage(Model model) {		
		//ブログテーブルの中からユーザーIDを使って、そのユーザーが書いたブログ記事のみを取得する
		List<Blog> blogList = blogService.selectByAll();
		//blogList（ブログの記事一覧情報）とuserName（管理者の名前）をmodelにセットし
		//admin_blog_all.htmlから参照可能にする。
		model.addAttribute("blogList",blogList);
		return "homepage.html";
	}
	
	@GetMapping("/userhomepage")
	public ModelAndView getUserHomePage(Principal principal) {
		ModelAndView mav = new ModelAndView("homepage.html");
		String userName = principal.getName();
		mav.addObject("name", userName);
		
		//ブログテーブルの中からユーザーIDを使って、そのユーザーが書いたブログ記事のみを取得する
		List<Blog> blogList = blogService.selectByAll();
		//blogList（ブログの記事一覧情報）とuserName（管理者の名前）をmodelにセットし
		//admin_blog_all.htmlから参照可能にする。
		mav.addObject("blogList",blogList);
		return mav;
	}
	
	@GetMapping("/loginerror")
	public ModelAndView getUserErrorPage(Principal principal) {
		ModelAndView mav = new ModelAndView("login.html");
		mav.addObject("error", true);
		return mav;
	}
	
//	@PostMapping("/user-login")
//	public ModelAndView getUserHomePage(@RequestParam String userName,
//			@RequestParam String userPassword, ModelAndView mav) {
//		if (accountService.validateAccount(userName, userPassword)) {
//			mav.addObject("name", userName);
//			mav.setViewName("homepage.html");
//		} else {
//			mav.addObject("error", true);
//			mav.setViewName("login.html");
//		}
//		return mav;
//	}

}


