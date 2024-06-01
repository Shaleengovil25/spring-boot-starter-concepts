package com.shaleen.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.shaleen.entity.User;
import com.shaleen.helper.Util;
import com.shaleen.repo.UserRepo;

@ControllerAdvice
public class RootController {
	
	@Autowired
	UserRepo userRepo;
	
	@ModelAttribute
	public void addLoggedInUserInfo(Authentication auth, Model model) {
		if(auth!=null) {
			Util util = new Util();
			String email = util.getEmailOfLoggedInUser(auth);
			Optional<User> user = userRepo.findByEmailId(email);
			model.addAttribute("loggedInUser",user.get());
		}
	}

}
