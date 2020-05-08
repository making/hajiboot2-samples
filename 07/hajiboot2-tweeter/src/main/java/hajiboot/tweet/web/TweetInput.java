package hajiboot.tweet.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class TweetInput {
    private final String text;

    @JsonCreator
    public TweetInput(@JsonProperty("text") String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
