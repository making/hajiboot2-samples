package com.example.hajiboot2markdownprinter;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Rule;
import org.junit.Test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.rule.OutputCapture;

import static org.assertj.core.api.Assertions.assertThat;

// @RunWith(SpringRunner.class)
// @SpringBootTest
public class Hajiboot2MarkdownPrinterApplicationTests {
	@Rule
	public OutputCapture capture = new OutputCapture();

	@Test
	public void contextLoads() {
		System.setIn(new ByteArrayInputStream(
				"Hello **Spring Boot**".getBytes(StandardCharsets.UTF_8)));
		SpringApplication.run(Hajiboot2MarkdownPrinterApplication.class);
		assertThat(capture.toString())
				.contains("<p>Hello <strong>Spring Boot</strong></p>");
		assertThat(capture.toString()).doesNotContain("RestTemplate");
	}

	@Test
	public void contextLoadsMarkdownTypeMarked4j() {
		System.setIn(new ByteArrayInputStream(
				"Hello **Spring Boot**".getBytes(StandardCharsets.UTF_8)));
		SpringApplication.run(Hajiboot2MarkdownPrinterApplication.class,
				"--hajiboot2.markdown.type=marked4j");
		assertThat(capture.toString())
				.contains("<p>Hello <strong>Spring Boot</strong></p>");
		assertThat(capture.toString()).doesNotContain("RestTemplate");
	}

	@Test
	public void contextLoadsMarkdownTypeGithub() {
		System.setIn(new ByteArrayInputStream(
				"Hello **Spring Boot**".getBytes(StandardCharsets.UTF_8)));
		SpringApplication.run(Hajiboot2MarkdownPrinterApplication.class,
				"--hajiboot2.markdown.type=github");
		assertThat(capture.toString())
				.contains("<p>Hello <strong>Spring Boot</strong></p>");
		assertThat(capture.toString()).contains("RestTemplate");
	}
}
