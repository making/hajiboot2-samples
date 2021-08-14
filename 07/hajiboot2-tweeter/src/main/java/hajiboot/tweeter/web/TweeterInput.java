package hajiboot.tweeter.web;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class TweeterInput {
	@NotBlank
	@Size(max = 128)
	private final String username;

	@Email
	@NotBlank
	@Size(max = 128)
	private final String email;

	@NotBlank
	@Size(max = 255)
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