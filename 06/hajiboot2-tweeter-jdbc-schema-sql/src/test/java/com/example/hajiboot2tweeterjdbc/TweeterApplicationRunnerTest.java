package com.example.hajiboot2tweeterjdbc;

import org.junit.Rule;
import org.junit.Test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.rule.OutputCapture;

import static org.assertj.core.api.Assertions.assertThat;

public class TweeterApplicationRunnerTest {
	@Rule
	public OutputCapture capture = new OutputCapture();

	@Test
	public void contextLoads() {
		SpringApplication.run(Hajiboot2TweeterJdbcApplication.class);
		String output = capture.toString();
		assertThat(output).contains("Number of tweets: 2");
		assertThat(output).containsPattern(
				"Tweet\\{uuid=[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}, text='Demo1', username='making', createdAt=[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}Z\\}");
		assertThat(output).containsPattern(
				"Tweet\\{uuid=[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}, text='Demo2', username='making', createdAt=[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}Z\\}");
	}

}