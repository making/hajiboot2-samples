package com.example.hajiboot2markdownprinter;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Online
public class GitHubApiMarkdownRenderer implements MarkdownRenderer {
	private final RestTemplate restTemplate;

	public GitHubApiMarkdownRenderer(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public String render(String markdown) {
		return this.restTemplate.postForObject("https://api.github.com/markdown/raw",
				markdown, String.class);
	}
}
