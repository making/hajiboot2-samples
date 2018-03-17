package com.example.hajiboot2markdownprinter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@ConditionalOnProperty(name = "hajiboot2.markdown.type", havingValue = "github")
public class GitHubApiMarkdownRenderer implements MarkdownRenderer {
	private final RestTemplate restTemplate;

	public GitHubApiMarkdownRenderer(GitHubProperties props,
			RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder
				.interceptors((request, body, execution) -> {
					request.getHeaders().add("Authorization",
							"token " + props.getAccessToken());
					return execution.execute(request, body);
				}).build();
	}

	@Override
	public String render(String markdown) {
		return this.restTemplate.postForObject("https://api.github.com/markdown/raw",
				markdown, String.class);
	}
}
