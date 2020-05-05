package hajiboot;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(OutputCaptureExtension.class)
// @SpringBootTest // (1)
class Hajiboot2MarkdownPrinterApplicationTests {

	@Test
	void contextLoads(CapturedOutput capture) {
        System.setIn(new ByteArrayInputStream(
                "Hello **Spring Boot**".getBytes(StandardCharsets.UTF_8))); // (2)
        SpringApplication.run(Hajiboot2MarkdownPrinterApplication.class); // (3)
        assertThat(capture.toString())
                .contains("<p>Hello <strong>Spring Boot</strong></p>"); // (4)
	}

}
