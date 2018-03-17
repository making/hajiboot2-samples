package com.example.hajiboot2markdownprinter;

import am.ik.marked4j.Marked;
import am.ik.marked4j.MarkedBuilder;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "hajiboot2.markdown.type", havingValue = "marked4j", matchIfMissing = true)
public class Marked4jMarkdownRenderer implements MarkdownRenderer {
	private final Marked marked = new MarkedBuilder().build();

	@Override
	public String render(String markdown) {
		return this.marked.marked(markdown);
	}
}
