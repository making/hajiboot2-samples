package com.example.hajiboot2markdownprinter;

import java.util.function.Function;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class DetectSlowExecutionAspectTest {
	@Rule
	public OutputCapture capture = new OutputCapture();
	@Autowired
	Sleep sleep;

	@Test
	public void detectSlow() {
		sleep.apply(1000L);
		String log = capture.toString();
		assertThat(log).contains("Detect slow execution elapsed=");
		assertThat(log).contains(", method=public java.lang.String "
				+ Sleep.class.getName() + ".apply(java.lang.Long), args=[1000]");
	}

	@Test
	public void notDetected() {
		sleep.apply(100L);
		String log = capture.toString();
		assertThat(log).doesNotContain("Detect slow execution elapsed=");
	}

	@Configuration
	@EnableAspectJAutoProxy(proxyTargetClass = true)
	public static class TestConfig {
		@Bean
		public DetectSlowExecutionAspect detectSlowExecutionAspect() {
			return new DetectSlowExecutionAspect();
		}

		@Bean
		public Sleep sleep() {
			return new Sleep();
		}
	}

	public static class Sleep implements Function<Long, String> {
		@DetectSlowExecution(threshold = 800)
		@Override
		public String apply(Long sleep) {
			try {
				Thread.sleep(sleep);
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			return "Slept " + sleep + " [msec]";
		}
	}
}