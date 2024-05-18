package com.shaleen.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.shaleen.entity.User;
import com.shaleen.helper.Providers;
import com.shaleen.repo.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OauthSuccessHandler implements AuthenticationSuccessHandler {

	Logger log = LoggerFactory.getLogger(OauthSuccessHandler.class);

	@Autowired
	UserRepo userRepo;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.info("onAuthenticationSuccess");

		OAuth2AuthenticationToken oAuthtoken = (OAuth2AuthenticationToken) authentication;
		String token = oAuthtoken.getAuthorizedClientRegistrationId();
		log.info("provider token--> " + token);

		DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
//		user.getAttributes().forEach((key,val)->{
//			log.info("{} => {}",key,val);
//		});

		User userbo = new User();
		
		if ("google".equalsIgnoreCase(token)) {
			userbo = saveGoogleUserToDb(user);

		} else if ("github".equalsIgnoreCase(token)) {
			userbo = saveGithubUserToDb(user);
		} else {
			log.info("Unknown provider");
			throw new RuntimeException("Unknown provider");
		}

		if (userRepo.findByEmailId(userbo.getEmailId()).isEmpty()) {
			userRepo.save(userbo);
			log.info("user saved to db..");

		}
		
		new DefaultRedirectStrategy().sendRedirect(request, response, "/user");

	}

	private User saveGithubUserToDb(DefaultOAuth2User user) {
		// TODO Auto-generated method stub
		User userbo = new User();
		userbo.setEmailId(user.getAttribute("email"));
		userbo.setName(user.getAttribute("name"));
		userbo.setEmailVerified(true);
		userbo.setProfilePic(user.getAttribute("avatar_url"));
		userbo.setProvider(Providers.GITHUB);
		userbo.setProviderUserId(user.getName());
		
		userbo.setPassword("user@github");
		userbo.setPhoneNumber("NA");
		userbo.setAbout(user.getAttribute("bio"));
		return userbo;
	}

	private User saveGoogleUserToDb(DefaultOAuth2User user) {
		// TODO Auto-generated method stub
		User userbo = new User();
		userbo.setEmailId(user.getAttribute("email"));
		userbo.setName(user.getAttribute("name"));
		userbo.setEmailVerified(user.getAttribute("email_verified"));
		userbo.setProfilePic(user.getAttribute("picture"));
		userbo.setProvider(Providers.GOOGLE);
		userbo.setProviderUserId(user.getName());
		
		userbo.setPassword("user@google");
		userbo.setPhoneNumber("NA");
		userbo.setAbout("This account is created using google..");
		return userbo;
	}

}
