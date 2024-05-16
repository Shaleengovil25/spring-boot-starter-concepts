package com.shaleen.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shaleen.entity.User;
import com.shaleen.repo.UserRepo;

@Service
public class CustomSecurityUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		return userRepo.findByEmailId(username)
				.orElseThrow(()-> new UsernameNotFoundException("user not found with email "+username));
	}

}
