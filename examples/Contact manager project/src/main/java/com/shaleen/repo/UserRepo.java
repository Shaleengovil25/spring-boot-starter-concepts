package com.shaleen.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shaleen.entity.User;

public interface UserRepo extends JpaRepository<User, String>{
	Optional<User> findByEmailId(String emailID);
}
