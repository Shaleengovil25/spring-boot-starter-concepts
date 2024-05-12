package com.shaleen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shaleen.entity.User;

@Controller
public class PageController {
	
	@RequestMapping("/")
	public String index() {
		System.out.println("index handler called");
		return "redirect:/home";
	}
	
	@RequestMapping("/home")
	public String home() {
		System.out.println("home handler called");
		return "home";
	}
	
	@RequestMapping("/about")
	public String about(Model model) {
		System.out.println("about handler called");
		model.addAttribute("title", "About");
		model.addAttribute("content", "This is my about page");
		return "about";
	}
	
	@RequestMapping("/services")
	public String services() {
		System.out.println("services handler called");
		return "services";
	}
	
	@RequestMapping("/contact")
	public String contact() {
		System.out.println("contact handler called");
		return "contact";
	}
	
	@RequestMapping("/login")
	public String login() {
		System.out.println("login handler called");
		return "login";
	}
	
	@RequestMapping("/signup")
	public String signup(Model model) {
		System.out.println("signup handler called");
		model.addAttribute("user", new User());
		return "signup";
	}
	
	@PostMapping("/do-register")
	public String doRegister(@ModelAttribute User user) {
		System.out.println("do-register handler called");
		System.out.println(user.getName());
		System.out.println(user.getEmailId());
		System.out.println(user.getAbout());
		System.out.println(user.getPassword());
		System.out.println(user.getPhoneNumber());
		return "redirect:/signup";
	}

}
