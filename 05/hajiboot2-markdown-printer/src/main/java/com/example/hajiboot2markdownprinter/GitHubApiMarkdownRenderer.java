package com.example.hajiboot2markdownprinter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.util.StreamUtils;

public class GitHubApiMarkdownRenderer implements MarkdownRenderer {
	@Override
	public String render(String markdown) {
		try {
			URL url = new URL("https://api.github.com/markdown/raw");
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "text/plain");
			conn.setRequestProperty("Content-Length",
					String.valueOf(markdown.getBytes(StandardCharsets.UTF_8).length));
			conn.setDoOutput(true);
			try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
				wr.writeBytes(markdown);
				wr.flush();
			}
			try (InputStream inputStream = conn.getInputStream()) {
				return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
			}
		}
		catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
