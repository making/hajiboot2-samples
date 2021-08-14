package hajiboot.tweet.web;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class TweetInput {
	@NotBlank
	@Size(max = 140)
	private final String text;

	@NotBlank
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
