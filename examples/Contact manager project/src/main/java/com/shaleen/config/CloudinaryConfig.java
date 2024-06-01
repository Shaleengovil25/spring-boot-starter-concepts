package com.shaleen.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {
	
	@Value("${cloudinary.name}")
	String name;
	
	@Value("${cloudinary.apiKey}")
	String apiKey;
	
	@Value("${cloudinary.apiSecret}")
	String apiSecret;
	
	@Bean
	public Cloudinary cloudinary() {
		
		System.out.println("name"+name+" key: "+apiKey+" secret: "+apiSecret);
		return new Cloudinary(
				ObjectUtils.asMap(
						"cloud_name",name,
						"api_key",apiKey,
						"api_secret",apiSecret
						)
				);
	}

}
