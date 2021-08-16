package hajiboot.tweeter.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class TweeterInput {
	private final String username;

	private final String email;

	private final String password;

	@JsonCreator
	public TweeterInput(@JsonProperty("username") String username,
			@JsonProperty("email") String email,
			@JsonProperty("password") String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}