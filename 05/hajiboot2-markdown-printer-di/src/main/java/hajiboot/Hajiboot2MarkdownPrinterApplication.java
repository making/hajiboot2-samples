package hajiboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Hajiboot2MarkdownPrinterApplication {

	@Bean
	public MarkdownPrinter markdownPrinter(MarkdownRenderer markdownRenderer) {
		return new MarkdownPrinter(markdownRenderer);
	}

	@Bean
	public MarkdownRenderer markdownRenderer(RestTemplate restTemplate) {
		return new GitHubApiMarkdownRenderer(restTemplate);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication
				.run(Hajiboot2MarkdownPrinterApplication.class, args); // (1)
		MarkdownPrinter markdownPrinter = context.getBean(MarkdownPrinter.class); // (2)
		markdownPrinter.print(System.in);
	}
}