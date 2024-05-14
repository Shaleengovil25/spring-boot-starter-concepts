package com.shaleen.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {
	
	@NotBlank(message = "Name is required")
	private String name;
	
	@NotBlank(message = "Email is required")
	@Email
	private String emailId;
	
	@NotBlank(message = "Password is required")
	private String password;
	
	@NotBlank(message = "About is required")
	private String about;
	
	@NotBlank(message = "Phone Number is required")
	private String phoneNumber;

}
