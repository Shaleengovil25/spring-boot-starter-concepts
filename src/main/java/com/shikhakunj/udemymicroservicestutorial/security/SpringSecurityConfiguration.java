package com.shikhakunj.udemymicroservicestutorial.security;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// All requests should be authenticated
		http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
		
		// Show a popup if request is not authenticated
		http.httpBasic(withDefaults());
		
		//disable csrf (cross site request forgery) for post, put requests
		http.csrf().disable();
		
		return http.build();
	}
}
