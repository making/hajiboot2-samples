package hajiboot.tweet.web;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class TweetInput {
	@NotBlank
	@Size(max = 140)
	private final String text;

	@JsonCreator
	public TweetInput(@JsonProperty("text") String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
