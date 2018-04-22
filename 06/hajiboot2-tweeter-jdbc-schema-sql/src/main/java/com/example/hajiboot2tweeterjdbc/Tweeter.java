package com.example.hajiboot2tweeterjdbc;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class Tweeter implements Serializable {
	private final UUID uuid;
	private final String text;
	private final String username;
	private final Instant createdAt;

	public Tweeter(UUID uuid, String text, String username, Instant createdAt) {
		this.uuid = uuid;
		this.text = text;
		this.username = username;
		this.createdAt = createdAt;
	}

	public UUID getUuid() {
		return uuid;
	}

	public String getText() {
		return text;
	}

	public String getUsername() {
		return username;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	@Override
	public String toString() {
		return "Tweeter{" + "uuid=" + uuid + ", text='" + text + '\'' + ", username='"
				+ username + '\'' + ", createdAt=" + createdAt + '}';
	}
}
