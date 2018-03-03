package com.example.hajiboot2markdownprinter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Hajiboot2MarkdownPrinterApplication {
	@Bean
	public CommandLineRunner commandLineRunner(MarkdownPrinter markdownPrinter) {
		return args -> markdownPrinter.print(System.in);
	}

	@Bean
	public MarkdownPrinter markdownPrinter(MarkdownRenderer markdownRenderer) {
		return new MarkdownPrinter(markdownRenderer);
	}

	@Bean
	public MarkdownRenderer markdownRenderer(RestTemplate restTemplate) {
		return new GitHubApiMarkdownRenderer(restTemplate);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(Hajiboot2MarkdownPrinterApplication.class, args);
	}
}
