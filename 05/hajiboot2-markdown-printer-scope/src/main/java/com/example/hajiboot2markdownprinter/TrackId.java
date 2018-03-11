package com.example.hajiboot2markdownprinter;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TrackId {
	private static final AtomicLong counter = new AtomicLong(0);
	private final long value;

	public TrackId() {
		this.value = counter.incrementAndGet();
	}

	public long asLong() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(this.value);
	}
}
