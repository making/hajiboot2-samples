package com.example.hajiboot2markdownprinter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MarkdownPrinterCommandLineRunner implements CommandLineRunner {
	private final MarkdownPrinter markdownPrinter;

	public MarkdownPrinterCommandLineRunner(MarkdownPrinter markdownPrinter) {
		this.markdownPrinter = markdownPrinter;
	}

	@Override
	public void run(String... args) throws Exception {
		this.markdownPrinter.print(System.in);
	}
}
