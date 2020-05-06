package hajiboot;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Tweet implements Serializable {
	private final UUID uuid;
	private final String text;
	private final Tweeter tweeter;
	private final Instant createdAt;

	public Tweet(UUID uuid, String text, Tweeter tweeter, Instant createdAt) {
		this.uuid = uuid;
		this.text = text;
		this.tweeter = tweeter;
		this.createdAt = createdAt;
	}

	public UUID getUuid() {
		return uuid;
	}

	public String getText() {
		return text;
	}

	public Tweeter getTweeter() {
		return tweeter;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	@Override
	public String toString() {
		return "Tweet{" + "uuid=" + uuid + ", text='" + text + '\'' + ", tweeter="
				+ tweeter + ", createdAt=" + createdAt + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final Tweet tweet = (Tweet) o;
		return Objects.equals(uuid, tweet.uuid);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uuid);
	}
}