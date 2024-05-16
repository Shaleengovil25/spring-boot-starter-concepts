package com.shaleen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shaleen.entity.User;
import com.shaleen.model.Alert;
import com.shaleen.repo.UserRepo;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
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
	
	@RequestMapping(value = "/do-register", method = RequestMethod.POST)
	public String doRegister(@Valid @ModelAttribute User user, BindingResult rBindingResult, HttpSession session) {
		System.out.println("do-register called");
		
		if(rBindingResult.hasErrors()) {
			// working only if this argument is after @Valid argument
			System.out.println("error present");
			return "signup";
		}
		
		Alert alert = new Alert();
		try {
//			User user = new User();
//			user.setName(userForm.getName());
//			user.setEmailId(userForm.getEmailId());
//			user.setPhoneNumber(userForm.getPhoneNumber());
//			user.setPassword(userForm.getPassword());
//			user.setAbout(userForm.getAbout());
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRoleList(List.of("User","Approver"));
			userRepo.save(user);
			alert.setContent("User registeration is successfull !");
			alert.setType("SUCCESS");
			
		}
		catch(Exception e) {
			alert.setContent("User registeration is failed, please try again");
			alert.setType("FAILURE");
		}
		
		session.setAttribute("message", alert);
		return "redirect:/signup";
	}

}
