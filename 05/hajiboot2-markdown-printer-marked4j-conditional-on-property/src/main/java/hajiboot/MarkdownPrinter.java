package hajiboot;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

@Component
public class MarkdownPrinter {
	private final MarkdownRenderer renderer;

	public MarkdownPrinter(MarkdownRenderer renderer) {
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