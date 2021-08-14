package hajiboot.tweet.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class TweetInput {
	private final String text;

	private final String username;

	@JsonCreator
	public TweetInput(@JsonProperty("text") String text,
			@JsonProperty("username") String username /* TODO from Authentication */) {
		this.text = text;
		this.username = username;
	}

	public String getText() {
		return text;
	}

	public String getUsername() {
		return username;
	}
}
