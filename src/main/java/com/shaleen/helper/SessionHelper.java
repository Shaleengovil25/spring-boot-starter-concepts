package com.shaleen.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {
	public static void removeMessage() {
		try {
			System.out.println("message removed");
			HttpSession session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
					.getRequest().getSession();
			session.removeAttribute("message");
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
