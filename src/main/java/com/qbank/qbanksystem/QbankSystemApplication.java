package com.qbank.qbanksystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class QbankSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(QbankSystemApplication.class, args);
	}
}
