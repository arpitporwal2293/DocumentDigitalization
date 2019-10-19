package com.m2l2.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.m2l2")
@EntityScan("com.m2l2")
public class DocumentDigitalization {

	public static void main(String[] args) {
		SpringApplication.run(DocumentDigitalization.class, args);
	}
}

