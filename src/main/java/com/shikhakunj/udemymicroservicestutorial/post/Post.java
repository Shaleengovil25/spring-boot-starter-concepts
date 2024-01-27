package com.shikhakunj.udemymicroservicestutorial.post;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shikhakunj.udemymicroservicestutorial.user.User;
import com.shikhakunj.udemymicroservicestutorial.user.audit.AuditLog;

@Entity
public class Post extends AuditLog{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq")
	@SequenceGenerator(name = "post_seq",allocationSize = 1)
	private int post_id;
	
	@Size(min=10)
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private User user;
	
	public int getId() {
		return post_id;
	}
	public void setId(int id) {
		this.post_id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
