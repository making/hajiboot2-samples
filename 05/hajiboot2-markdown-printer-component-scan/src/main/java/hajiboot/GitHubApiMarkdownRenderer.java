package hajiboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GitHubApiMarkdownRenderer implements MarkdownRenderer {
	private final RestTemplate restTemplate;

	@Autowired
	public GitHubApiMarkdownRenderer(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public String render(String markdown) {
		return this.restTemplate.postForObject("https://api.github.com/markdown/raw",
				markdown, String.class);
	}
}