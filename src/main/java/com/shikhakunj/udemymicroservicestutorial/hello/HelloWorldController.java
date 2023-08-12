package com.shikhakunj.udemymicroservicestutorial.hello;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	// for supporting the i18n (internationalization)
	private MessageSource messageSource;
	
	public HelloWorldController(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping("/hello")
	public String hello() {
		return "Hello World from Spring boot!";
	}
	
	// Java Class gets converted to JSON
	@GetMapping("/hello-bean")
	public HelloBean helloBean() {
		return new HelloBean("Hello ji!");
	}
	
	// Path Param
	@GetMapping("/hello/{name}")
	public String helloPathVar(@PathVariable String name) {
		return "Hello "+name;
	}
	
	@GetMapping("/hello-internationalized")
	public String helloIntenationalized() {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage("good.morning.message", null, "Default Message", locale);
	}
	
	
//	en - English - Good Morning
//	nl - dutch - Goedemorgen
//	fr - french - Bonjour
//	es - spanish - buen dia
//	de - deutsch - Guten Morgen
}
