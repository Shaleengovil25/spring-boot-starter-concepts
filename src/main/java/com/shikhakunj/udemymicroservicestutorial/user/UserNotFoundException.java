package com.shikhakunj.udemymicroservicestutorial.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {


	public UserNotFoundException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}

}
