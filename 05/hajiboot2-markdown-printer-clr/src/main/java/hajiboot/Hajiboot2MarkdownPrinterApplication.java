package hajiboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Hajiboot2MarkdownPrinterApplication {
	@Bean
	public CommandLineRunner commandLineRunner(MarkdownPrinter markdownPrinter) { // (1)
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				markdownPrinter.print(System.in);
			}
		};
	}

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
		SpringApplication.run(Hajiboot2MarkdownPrinterApplication.class, args);
	}
}