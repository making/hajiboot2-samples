package hajiboot.tweet.web;

import java.time.Instant;
import java.util.UUID;

class TweetForRead {
    private final UUID uuid;
    private final String text;
    private final String username;
    private final Instant createdAt;

    public TweetForRead(UUID uuid, String text, String username, Instant createdAt) {
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
}
