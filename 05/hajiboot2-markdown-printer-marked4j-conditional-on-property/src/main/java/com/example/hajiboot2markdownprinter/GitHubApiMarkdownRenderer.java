package com.example.hajiboot2markdownprinter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@ConditionalOnProperty(name = "hajiboot2.markdown.type", havingValue = "github")
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
