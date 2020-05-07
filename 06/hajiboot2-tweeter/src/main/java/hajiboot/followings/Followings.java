package hajiboot.followings;

import hajiboot.tweeter.Tweeter;

import java.util.Collections;
import java.util.List;

public class Followings {
    private final String username;
    private final List<Tweeter> tweeters;

    public Followings(String username, List<Tweeter> tweeters) {
        this.username = username;
        this.tweeters = Collections.unmodifiableList(tweeters);
    }

    public String getUsername() {
        return username;
    }

    public List<Tweeter> getTweeters() {
        return tweeters;
    }

    @Override
    public String toString() {
        return "Followings{" +
                "username='" + username + '\'' +
                ", number of tweeters=" + (tweeters == null ? 0 : tweeters.size()) +
                '}';
    }
}
