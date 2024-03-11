package com.example.monitormail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling 
public class MonitormailApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitormailApplication.class, args);
	}

}
