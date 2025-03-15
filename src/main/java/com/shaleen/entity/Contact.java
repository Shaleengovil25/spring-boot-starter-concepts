package com.shaleen.entity;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Contact {
	
	@Id
	@GeneratedValue(generator = "contact_id")
	@GenericGenerator(name = "contact_id", 
	strategy = "com.shaleen.helper.generator.CustomIdGenerator",
	parameters = @Parameter(name="prefix",value="contact-"))
	private String contactId;
	
	@NotBlank(message = "Contact name is required")
	private String name;
	
	@NotBlank(message = "Contact email is required")
	@Email(message = "Invalid email")
	@Column(unique = true, nullable = false)
	private String email;
	
	@NotBlank(message = "Contact number is required")
	@Column(unique = true, nullable = false)
	private String phoneNumber;
	
	@NotBlank(message = "Contact description is required")
	@Column(columnDefinition = "text")
	private String description;
	
	private boolean favourite = false;
	
	private String address;
	
	@Column(columnDefinition = "text")
	private String picture;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;
	
	@OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<SocialLink> socialLinks;
	
	@Transient
	private String websiteLink;
	
	@Transient
	private String linkedinLink;
	
	@Transient
	private MultipartFile contactImage;

	

}
