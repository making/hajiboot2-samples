package hajiboot;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TweeterApplicationRunner implements ApplicationRunner {
	private final JdbcTemplate jdbcTemplate;

	public TweeterApplicationRunner(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		this.jdbcTemplate.execute("CREATE TABLE tweets (\n" + //
				"  uuid       VARCHAR(36) PRIMARY KEY,\n" + //
				"  text       VARCHAR(255),\n" + //
				"  username   VARCHAR(128),\n" + //
				"  created_at TIMESTAMP\n" + //
				");");
		this.jdbcTemplate.update(
				"INSERT INTO tweets(uuid, text, username, created_at) VALUES(?,?,?,?)",
				UUID.randomUUID(), "Demo1", "making", Instant.now());
		this.jdbcTemplate.update(
				"INSERT INTO tweets(uuid, text, username, created_at) VALUES(?,?,?,?)",
				UUID.randomUUID(), "Demo2", "making", Instant.now());
		Long count = this.jdbcTemplate.queryForObject("SELECT count(*) FROM tweets",
				Long.class);
		System.out.println("Number of tweets: " + count);
		List<Tweet> tweets = this.jdbcTemplate.query(
				"SELECT uuid, text, username, created_at FROM tweets",
				(rs, i) -> new Tweet(UUID.fromString(rs.getString("uuid")),
						rs.getString("text"), rs.getString("username"),
						rs.getTimestamp("created_at").toInstant()));
		tweets.forEach(tweet -> System.out.println(tweet));
	}
}
