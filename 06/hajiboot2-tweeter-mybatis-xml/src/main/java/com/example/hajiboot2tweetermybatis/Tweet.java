package com.example.hajiboot2tweetermybatis;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Tweet implements Serializable {
	private UUID uuid;
	private String text;
	private String username;
	private Instant createdAt;

	public Tweet() {
	}

	public Tweet(UUID uuid, String text, String username, Instant createdAt) {
		this.uuid = uuid;
		this.text = text;
		this.username = username;
		this.createdAt = createdAt;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Tweet{" + "uuid=" + uuid + ", text='" + text + '\'' + ", username='"
				+ username + '\'' + ", createdAt=" + createdAt + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Tweet tweet = (Tweet) o;
		return Objects.equals(uuid, tweet.uuid);
	}

	@Override
	public int hashCode() {

		return Objects.hash(uuid);
	}
}
