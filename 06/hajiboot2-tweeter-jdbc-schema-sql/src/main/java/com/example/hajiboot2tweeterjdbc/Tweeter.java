package com.example.hajiboot2tweeterjdbc;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class Tweeter implements Serializable {
    private final UUID uuid;
    private final String text;
    private final String username;
    private final Instant created;

    public Tweeter(UUID uuid, String text, String username, Instant created) {
        this.uuid = uuid;
        this.text = text;
        this.username = username;
        this.created = created;
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

    public Instant getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return "Tweeter{" +
                "uuid=" + uuid +
                ", text='" + text + '\'' +
                ", username='" + username + '\'' +
                ", created=" + created +
                '}';
    }
}
