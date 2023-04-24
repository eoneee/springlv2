package com.example.springlv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Springlv2Application {

	public static void main(String[] args) {
		SpringApplication.run(Springlv2Application.class, args);
	}

}
