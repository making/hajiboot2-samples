package com.example.hajiboot2markdownprinter;

import am.ik.marked4j.Marked;
import am.ik.marked4j.MarkedBuilder;

import org.springframework.stereotype.Component;

@Component
@Offline
public class Marked4jMarkdownRenderer implements MarkdownRenderer {
	private final Marked marked = new MarkedBuilder().build();

	@Override
	public String render(String markdown) {
		return this.marked.marked(markdown);
	}
}
