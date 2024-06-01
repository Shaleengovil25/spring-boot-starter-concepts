package com.shaleen.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class Util {
	
	Logger log = LoggerFactory.getLogger(Util.class);
	
	public String getEmailOfLoggedInUser(Authentication auth) {
		
		if(auth instanceof OAuth2AuthenticationToken ) {
			OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) auth;
			String clientId = token.getAuthorizedClientRegistrationId();
			DefaultOAuth2User user = (DefaultOAuth2User) auth.getPrincipal();
//			if("google".equalsIgnoreCase(clientId)) {
//				return user.getAttribute("email");
//			}
//			if("github".equalsIgnoreCase(clientId)) {
//				return user.getAttribute("email");
//			}
			log.info("oauth user: "+ clientId);
			return user.getAttribute("email");
		}
		else {
			// local account user
			log.info("local db user");
			return auth.getName();
		}
	}

}
