package com.shaleen.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shaleen.entity.Contact;
import com.shaleen.entity.SocialLink;
import com.shaleen.entity.User;
import com.shaleen.helper.Util;
import com.shaleen.model.Alert;
import com.shaleen.repo.ContactRepo;
import com.shaleen.repo.LinkRepo;
import com.shaleen.repo.UserRepo;
import com.shaleen.service.ImageService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserPageController {
	
	Logger log = LoggerFactory.getLogger(UserPageController.class);
	
	@Autowired LinkRepo linkRepo;
	
	@Autowired ContactRepo contactRepo;
	
	@Autowired UserRepo userRepo;
	
	@Autowired ImageService imageService;
	
	@RequestMapping("/profile")
	public String profile() {
		log.info("profile handler called");
		return "user/profile";
	}
	
	@RequestMapping("/dashboard")
	public String dashboard() {
		log.info("dashboard handler called");
		return "user/dashboard";
	}
	
	@RequestMapping("/add/contact")
	public String addContact(Model model) {
		log.info("add_contact handler called");
		model.addAttribute("contact", new Contact());
		return "user/add_contact";
	}
	
	@RequestMapping(value="/add/contact", method = RequestMethod.POST )
	public String processContact(@Valid @ModelAttribute Contact contact, BindingResult rBindingResult, Authentication auth, HttpSession session) {
		log.info("process_contact handler called");
		log.info(contact.toString());
		
		if(rBindingResult.hasErrors()) {
			// working only if this argument is after @Valid argument
			log.info("error present");
			log.info("count: "+rBindingResult.getErrorCount());
			log.info(rBindingResult.getAllErrors().toString());
			return "user/add_contact";
		}
		
		log.info("file name: "+contact.getContactImage().getOriginalFilename());
		String fileUrl = imageService.uploadImage(contact.getContactImage());
		
		Alert alert = new Alert();
		try {
			Util util = new Util();
			String username = util.getEmailOfLoggedInUser(auth);
			Optional<User> user = userRepo.findByEmailId(username);
			contact.setUser(user.get());
			contact.setPicture(fileUrl);
			Contact savedContact = contactRepo.save(contact);
			
			saveLinks(savedContact, contact.getWebsiteLink(), contact.getLinkedinLink());
			
			alert.setContent("Contact added successfully !");
			alert.setType("SUCCESS");
			
		}
		catch(Exception e) {
			alert.setContent("Failed to add the contact !");
			alert.setType("FAILURE");
		}
		
		session.setAttribute("message", alert);
		return "redirect:/user/add/contact";
	}

	private void saveLinks(Contact savedContact, String websiteLink, String linkedinLink) {
		// TODO Auto-generated method stub
		List<SocialLink> links = new ArrayList<>();
		SocialLink link1 = new SocialLink();
		link1.setLink("website link");
		link1.setTitle(websiteLink);
		link1.setContact(savedContact);
		links.add(link1);
		
		SocialLink link2 = new SocialLink();
		link2.setLink("linkedin link");
		link2.setTitle(linkedinLink);
		link2.setContact(savedContact);
		links.add(link2);
		
		linkRepo.saveAll(links);
	}

}
