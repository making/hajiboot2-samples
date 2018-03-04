package com.example.hajiboot2markdownprinter;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

@Component
public class MarkdownPrinter {
	private final MarkdownRenderer renderer;

	public MarkdownPrinter(
			@Qualifier("gitHubApiMarkdownRenderer") MarkdownRenderer renderer) {
		this.renderer = renderer;
	}

	public void print(InputStream stream) {
		System.out.print("Input markdown: ");
		try {
			String markdown = StreamUtils.copyToString(stream, StandardCharsets.UTF_8);
			String html = this.renderer.render(markdown);
			System.out.println(html);
		}
		catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}