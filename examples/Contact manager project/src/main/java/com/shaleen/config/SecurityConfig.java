package com.shaleen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.shaleen.service.CustomSecurityUserDetailsService;

import jakarta.servlet.http.HttpSession;

@Configuration
public class SecurityConfig {
	
	@Autowired
	CustomSecurityUserDetailsService customSecurityUserDetailsService;

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		
		
		daoAuthenticationProvider.setUserDetailsService(customSecurityUserDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.authorizeHttpRequests(authorize->{
			
//			authorize.requestMatchers("/home","signup").permitAll();
			authorize.requestMatchers("/user/**").authenticated();
			authorize.anyRequest().permitAll();
			
		});
		
//		httpSecurity.formLogin(Customizer.withDefaults());
		httpSecurity.formLogin(loginform -> {
			
			loginform.loginPage("/login");
			loginform.loginProcessingUrl("/authenticate");
			loginform.successForwardUrl("/user/dashboard");
//			loginform.failureForwardUrl("/login?error=true");
			
			loginform.usernameParameter("email");
			loginform.passwordParameter("password");
			
		});
		
		httpSecurity.csrf(AbstractHttpConfigurer::disable);
		
		httpSecurity.logout(logoutform -> {
			logoutform.logoutUrl("/logout");
			
		});
		
		return httpSecurity.build();
	}
}
