package com.example.hajiboot2markdownprinter;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Rule;
import org.junit.Test;

import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindException;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

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
	public void contextLoadsMarkdownTypeGithub_invalid_token() {
		System.setIn(new ByteArrayInputStream(
				"Hello **Spring Boot**".getBytes(StandardCharsets.UTF_8)));
		try {
			SpringApplication.run(Hajiboot2MarkdownPrinterApplication.class,
					"--hajiboot2.markdown.type=github", "--github.access-token=foo");
			fail("Exception didn't happen");
		}
		catch (IllegalStateException e) {
			Throwable cause = e.getCause();
			assertThat(cause).isNotNull();
			assertThat(cause).isInstanceOf(HttpClientErrorException.class);
			assertThat(cause.getMessage()).isEqualTo("401 Unauthorized");
		}
		assertThat(capture.toString()).contains("RestTemplate");
	}

	@Test
	public void contextLoadsMarkdownTypeGithub_empty_token() {
		System.setIn(new ByteArrayInputStream(
				"Hello **Spring Boot**".getBytes(StandardCharsets.UTF_8)));
		try {
			SpringApplication.run(Hajiboot2MarkdownPrinterApplication.class,
					"--hajiboot2.markdown.type=github", "--github.access-token");
			fail("Exception didn't happen");
		}
		catch (UnsatisfiedDependencyException e) {
			Throwable cause = e.getCause();
			assertThat(cause).isNotNull();
			assertThat(cause).isInstanceOf(ConfigurationPropertiesBindException.class);
			assertThat(cause.getMessage())
					.contains("Could not bind properties to 'GitHubProperties'");
		}
		assertThat(capture.toString()).doesNotContain("RestTemplate");
	}

	// @Test // set your token and remove this comment
	public void contextLoadsMarkdownTypeGithub_valid_token() {
		String token = "YOUR-TOKEN";
		System.setIn(new ByteArrayInputStream(
				"Hello **Spring Boot**".getBytes(StandardCharsets.UTF_8)));
		SpringApplication.run(Hajiboot2MarkdownPrinterApplication.class,
				"--hajiboot2.markdown.type=github", "--github.access-token=" + token);
		assertThat(capture.toString())
				.contains("<p>Hello <strong>Spring Boot</strong></p>");
		assertThat(capture.toString()).contains("RestTemplate");
	}
}
