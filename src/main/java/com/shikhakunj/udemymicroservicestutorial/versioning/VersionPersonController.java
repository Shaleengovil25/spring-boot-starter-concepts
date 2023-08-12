package com.shikhakunj.udemymicroservicestutorial.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionPersonController {
	
	// URL Versioning - Twitter
	@GetMapping("/v1/person")
	public Person getFirstVersionOfPerson() {
		return new Person("Shaleen Govil");
	}
	
	@GetMapping("/v2/person")
	public PersonModified getSecondVersionOfPerson() {
		return new PersonModified(new Name("Shaleen","Govil"));
	}
	
	// Request Param Versioning - Amazon
	@GetMapping(path="/person", params="version=1")
	public Person getFirstVersionOfPersonUsingReqParam() {
		return new Person("Shaleen Govil");
	}
	
	@GetMapping(path="/person", params="version=2")
	public PersonModified getSecondVersionOfPersonUsingReqParam() {
		return new PersonModified(new Name("Shaleen","Govil"));
	}
	
	// Request Header(custom) Versioning - Microsoft
	@GetMapping(path="/person/header", headers="x-api-version=1")
	public Person getFirstVersionOfPersonUsingReqHeader() {
		return new Person("Shaleen Govil");
	}
	
	@GetMapping(path="/person/header", headers="x-api-version=2")
	public PersonModified getSecondVersionOfPersonUsingReqHeaer() {
		return new PersonModified(new Name("Shaleen","Govil"));
	}
	
	// Accept Header Versioning - Github
	@GetMapping(path="/person/accept-header", produces="application/vnd.company.app-v1+json")
	public Person getFirstVersionOfPersonUsingAcceptHeader() {
		return new Person("Shaleen Govil");
	}
	
	@GetMapping(path="/person/accept-header", produces="application/vnd.company.app-v2+json")
	public PersonModified getSecondVersionOfPersonUsingAcceptHeaer() {
		return new PersonModified(new Name("Shaleen","Govil"));
	}

}
