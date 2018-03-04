package com.example.hajiboot2markdownprinter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import org.springframework.boot.test.rule.OutputCapture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

public class MarkdownPrinterTest {
	@Rule
	public OutputCapture capture = new OutputCapture();

	@Test
	public void print() {
		MarkdownRenderer markdownRenderer = Mockito.mock(MarkdownRenderer.class);
		given(markdownRenderer.render(anyString())).willReturn("<p>Markdown!</p>");

		MarkdownPrinter printer = new MarkdownPrinter(markdownRenderer);
		InputStream stream = new ByteArrayInputStream(
				"Markdown!".getBytes(StandardCharsets.UTF_8));
		printer.print(stream);

		assertThat(capture.toString()).contains("Input markdown:");
		assertThat(capture.toString()).contains("<p>Markdown!</p>");
	}
}