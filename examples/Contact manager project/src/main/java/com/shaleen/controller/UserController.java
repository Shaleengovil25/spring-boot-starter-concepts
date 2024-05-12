package com.shaleen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shaleen.entity.Contact;
import com.shaleen.entity.SocialLink;
import com.shaleen.entity.User;
import com.shaleen.repo.ContactRepo;
import com.shaleen.repo.LinkRepo;
import com.shaleen.repo.UserRepo;

@RestController
public class UserController {
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	ContactRepo contactRepo;
	
	@Autowired
	LinkRepo linkRepo;
	
	@GetMapping("/users")
	public List<User> getUsers(){
		return userRepo.findAll();
	}
	
	@GetMapping("/contacts")
	public List<Contact> getContacts(){
		return contactRepo.findAll();
	}
	
	@GetMapping("/links")
	public List<SocialLink> getLinks(){
		return linkRepo.findAll();
	}
	
	@GetMapping("/users/create")
	public User createUser(User user){
		user.setName("nandu");
		user.setEmailId("abcdef@gmail.com");
		user.setAbout("This is info about user");
		return userRepo.save(user);
	}
	
	@GetMapping("/contacts/create")
	public Contact createContact(Contact contact){
		contact.setName("contact1");
		contact.setEmail("abcde@da.com");
		contact.setPhoneNumber("e38e900");
		return contactRepo.save(contact);
	}
	
	@GetMapping("/links/create")
	public SocialLink createLink(SocialLink link){
		link.setTitle("title1");
		return linkRepo.save(link);
	}

}
