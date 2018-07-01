package com.example.hajiboot2tweeterspringdatajdbc;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("tweets")
public class Tweet implements Serializable {
	@Id
	private UUID uuid;
	private final String text;
	private final String username;
	private final Instant createdAt;

	public Tweet(UUID uuid, String text, String username, Instant createdAt) {
		this.uuid = uuid;
		this.text = text;
		this.username = username;
		this.createdAt = createdAt;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
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
		return Objects.equals(uuid, tweet.uuid) && Objects.equals(text, tweet.text)
				&& Objects.equals(username, tweet.username)
				&& Objects.equals(createdAt, tweet.createdAt);
	}

	@Override
	public int hashCode() {

		return Objects.hash(uuid, text, username, createdAt);
	}
}
