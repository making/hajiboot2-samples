package hajiboot.tweeter;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TweeterMapper {
	private final JdbcTemplate jdbcTemplate;

	public TweeterMapper(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public long countByUsername(String username) {
		return this.jdbcTemplate.queryForObject(
				"SELECT count(*) FROM tweeters WHERE username = ?", Long.class, username);
	}

	public long countByEmail(String email) {
		return this.jdbcTemplate.queryForObject(
				"SELECT count(*) FROM tweeters WHERE email = ?", Long.class, email);
	}

	public boolean isUnusedUsername(String username) {
		return this.countByUsername(username) == 0;
	}

	public boolean isUnusedEmail(String email) {
		return this.countByEmail(email) == 0;
	}

	public Tweeter findByUsername(String username) {
		return this.jdbcTemplate.queryForObject("SELECT username, email, password, created_at FROM tweeters WHERE username = ?",
				(rs, i) -> new Tweeter(rs.getString("username"), rs.getString("email"), rs.getString("password"), rs.getTimestamp("created_at").toInstant()),
				username);
	}

	@Transactional
	public int insert(Tweeter tweeter) {
		return this.jdbcTemplate.update(
				"INSERT INTO tweeters(username, email, password, created_at) VALUES(?,?,?,?)",
				tweeter.getUsername(), tweeter.getEmail(), tweeter.getPassword(),
				tweeter.getCreatedAt());
	}
}