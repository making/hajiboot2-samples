package com.example.hajiboot2markdownprinter;

import am.ik.marked4j.Marked;
import am.ik.marked4j.MarkedBuilder;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class Marked4jMarkdownRenderer implements MarkdownRenderer {
	private final Marked marked = new MarkedBuilder().build();

	@Override
	public String render(String markdown) {
		return this.marked.marked(markdown);
	}
}
