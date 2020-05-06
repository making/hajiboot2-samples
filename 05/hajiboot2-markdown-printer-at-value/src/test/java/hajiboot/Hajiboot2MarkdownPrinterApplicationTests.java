package hajiboot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@ExtendWith(OutputCaptureExtension.class)
// @SpringBootTest
class Hajiboot2MarkdownPrinterApplicationTests {

	@Test
	void contextLoads(CapturedOutput capture) {
		System.setIn(new ByteArrayInputStream(
				"Hello **Spring Boot**".getBytes(StandardCharsets.UTF_8)));
		SpringApplication.run(Hajiboot2MarkdownPrinterApplication.class);
		assertThat(capture.toString())
				.contains("<p>Hello <strong>Spring Boot</strong></p>");
		assertThat(capture.toString()).doesNotContain("RestTemplate");
	}

	@Test
	void contextLoadsMarkdownTypeMarked4j(CapturedOutput capture) {
		System.setIn(new ByteArrayInputStream(
				"Hello **Spring Boot**".getBytes(StandardCharsets.UTF_8)));
		SpringApplication.run(Hajiboot2MarkdownPrinterApplication.class,
				"--hajiboot2.markdown.type=marked4j");
		assertThat(capture.toString())
				.contains("<p>Hello <strong>Spring Boot</strong></p>");
		assertThat(capture.toString()).doesNotContain("RestTemplate");
	}

	@Test
	void contextLoadsMarkdownTypeGithub_invalid_token(CapturedOutput capture) {
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
			assertThat(cause.getMessage()).contains("401 Unauthorized");
		}
		assertThat(capture.toString()).contains("RestTemplate");
	}

	// @Test // set your token and remove this comment
	void contextLoadsMarkdownTypeGithub_valid_token(CapturedOutput capture) {
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
