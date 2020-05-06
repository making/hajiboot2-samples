package hajiboot;

import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TweetMapper {
	private final JdbcTemplate jdbcTemplate;

	public TweetMapper(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public int insert(Tweet tweet) {
		return this.jdbcTemplate.update(
				"INSERT INTO tweets(uuid, text, username, created_at) VALUES(?,?,?,?)",
				tweet.getUuid(), tweet.getText(), tweet.getUsername(),
				tweet.getCreatedAt());
	}

	public long count() {
		return this.jdbcTemplate.queryForObject("SELECT count(*) FROM tweets",
				Long.class);
	}

	public List<Tweet> findAll() {
		return this.jdbcTemplate.query(
				"SELECT uuid, text, username, created_at FROM tweets",
				(rs, i) -> new Tweet(UUID.fromString(rs.getString("uuid")),
						rs.getString("text"), rs.getString("username"),
						rs.getTimestamp("created_at").toInstant()));
	}
}
