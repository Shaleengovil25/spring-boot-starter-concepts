package com.shikhakunj.udemymicroservicestutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UdemyMicroservicesTutorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(UdemyMicroservicesTutorialApplication.class, args);
	}

}
