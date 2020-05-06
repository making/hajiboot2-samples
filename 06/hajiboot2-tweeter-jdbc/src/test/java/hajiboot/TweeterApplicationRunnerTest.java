package hajiboot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(OutputCaptureExtension.class)
public class TweeterApplicationRunnerTest {

	@Test
	void contextLoads(CapturedOutput capture) {
		SpringApplication.run(Hajiboot2TweeterApplication.class);
		String output = capture.toString();
		assertThat(output).contains("Number of tweets: 2");
		assertThat(output).containsPattern(
				"Tweet\\{uuid=[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}, text='Demo1', username='making', createdAt=[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]+Z}");
		assertThat(output).containsPattern(
				"Tweet\\{uuid=[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}, text='Demo2', username='making', createdAt=[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]+Z}");
	}

}