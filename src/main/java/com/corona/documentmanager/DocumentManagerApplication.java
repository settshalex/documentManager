package com.corona.documentmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class DocumentManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentManagerApplication.class, args);
	}

}
