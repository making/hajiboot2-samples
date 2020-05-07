package hajiboot.tweet.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TweetForCreate {
    private final String text;

    @JsonCreator
    public TweetForCreate(@JsonProperty("text") String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
