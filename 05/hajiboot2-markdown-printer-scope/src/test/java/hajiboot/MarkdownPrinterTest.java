package hajiboot;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(OutputCaptureExtension.class)
class MarkdownPrinterTest {

	@Test
	void print(CapturedOutput capture) {
		MarkdownRenderer markdownRenderer = Mockito.mock(MarkdownRenderer.class);
		given(markdownRenderer.render(anyString())).willReturn("<p>Markdown!</p>");

		MarkdownPrinter printer = new MarkdownPrinter(markdownRenderer, new TrackId());
		InputStream stream = new ByteArrayInputStream(
				"Markdown!".getBytes(StandardCharsets.UTF_8));
		printer.print(stream);

		assertThat(capture.toString()).contains("Input markdown:");
		assertThat(capture.toString()).contains("<p>Markdown!</p>");
	}
}