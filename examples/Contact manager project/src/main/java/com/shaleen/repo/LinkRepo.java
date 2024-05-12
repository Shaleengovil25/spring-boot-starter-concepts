package com.shaleen.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shaleen.entity.SocialLink;

public interface LinkRepo extends JpaRepository<SocialLink, String>{

}
