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
	private final TrackId trackId;

	public MarkdownPrinter(MarkdownRenderer renderer, TrackId trackId) {
		this.renderer = renderer;
		this.trackId = trackId;
	}

	public void print(InputStream stream) {
		System.out.print("Input markdown: ");
		try {
			String markdown = StreamUtils.copyToString(stream, StandardCharsets.UTF_8);
			for (int i = 0; i < 3; i++) {
				String html = this.renderer.render(markdown);
				System.out.println("TrackId = " + trackId.asLong());
				System.out.println(html);
			}
		}
		catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}