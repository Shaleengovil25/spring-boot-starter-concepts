package com.shaleen.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shaleen.entity.Contact;

public interface ContactRepo extends JpaRepository<Contact, String>{

}
