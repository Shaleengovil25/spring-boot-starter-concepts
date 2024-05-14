package com.shaleen.entity;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.shaleen.helper.Providers;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(generator = "user_id")
	@GenericGenerator(name = "user_id", 
	strategy = "com.shaleen.helper.generator.CustomIdGenerator",
	parameters = @Parameter(name="prefix",value="user-"))
	private String userId;
	
	@NotBlank(message = "Name is required")
	@Column(nullable = false)
	private String name;
	
	@Email(message = "Invalid Email ID")
	@NotBlank(message = "Email is required")
	@Column(unique = true, nullable = false)
	private String emailId;
	
	@Size(min=6,message = "Minimum 6 characters are required")
	@NotBlank(message = "Password is required")
	private String password;
	
	@NotBlank(message = "About is required")
	@Column(columnDefinition = "text")
	private String about;
	
	@Column(columnDefinition = "text")
	private String profilePic;
	
	@NotBlank(message = "Phone Number is required")
	private String phoneNumber;
	
	private boolean enabled = false;
	
	private boolean emailVerified = false;
	
	private boolean phoneVerified = false;
	
	@Enumerated(value = EnumType.STRING)
	private Providers provider = Providers.SELF;
	
	private String providerUserId;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Contact> contacts;

}
