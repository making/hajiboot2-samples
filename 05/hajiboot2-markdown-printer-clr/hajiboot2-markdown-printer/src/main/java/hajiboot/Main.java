package hajiboot;

import org.springframework.web.client.RestTemplate;

public class Main {
	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
		MarkdownRenderer markdownRenderer = new GitHubApiMarkdownRenderer(restTemplate);
		MarkdownPrinter markdownPrinter = new MarkdownPrinter(markdownRenderer);
		markdownPrinter.print(System.in);
	}
}
