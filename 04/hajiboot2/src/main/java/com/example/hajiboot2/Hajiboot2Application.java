package com.example.hajiboot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController // (1)
public class Hajiboot2Application {
	
	@GetMapping(path = "/") // (2)
	public String hello() {
		return "Hello World!";
	}

	public static void main(String[] args) {
		SpringApplication.run(Hajiboot2Application.class, args);
	}
}
