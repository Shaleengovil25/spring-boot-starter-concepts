package com.shikhakunj.udemymicroservicestutorial.post;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class PostNotFoundException extends Exception {
	
	public PostNotFoundException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}

}
