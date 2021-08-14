package hajiboot.tweeter;

import java.time.Instant;
import java.util.Objects;

public class Tweeter {
	private final String username;

	private final String email;

	private final String password;

	private final Instant createdAt;

	public Tweeter(String username) {
		this(username, null, null, null);
	}

	public Tweeter(String username, String email, String password, Instant createdAt) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.createdAt = createdAt;
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

	public Instant getCreatedAt() {
		return createdAt;
	}

	@Override
	public String toString() {
		return "Tweeter{" + "username='" + username + '\'' + ", email='" + email + '\''
				+ '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Tweeter tweeter = (Tweeter) o;
		return Objects.equals(username, tweeter.username);
	}

	@Override
	public int hashCode() {

		return Objects.hash(username);
	}
}