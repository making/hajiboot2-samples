package com.example.hajiboot2markdownprinter;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MarkdownPrinterApplicationRunner implements ApplicationRunner {
	private final MarkdownPrinter markdownPrinter;

	public MarkdownPrinterApplicationRunner(MarkdownPrinter markdownPrinter) {
		this.markdownPrinter = markdownPrinter;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		this.markdownPrinter.print(System.in);
	}
}
