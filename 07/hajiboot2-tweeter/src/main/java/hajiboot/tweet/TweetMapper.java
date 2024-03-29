package hajiboot.tweet;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import hajiboot.tweeter.Tweeter;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TweetMapper {
	private final JdbcTemplate jdbcTemplate;

	final RowMapper<Tweet> tweetRowMapper = (rs, i) -> {
		Tweeter tweeter = new Tweeter(rs.getString("username"),
				rs.getString("email"), rs.getString("password"),
				rs.getTimestamp("tr_created_at").toInstant()); // (3)
		return new Tweet(UUID.fromString(rs.getString("uuid")),
				rs.getString("text"), tweeter,
				rs.getTimestamp("tw_created_at").toInstant());
	};

	public TweetMapper(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional
	public int insert(Tweet tweet) {
		return this.jdbcTemplate.update(
				"INSERT INTO tweets(uuid, text, username, created_at) VALUES(?,?,?,?)",
				tweet.getUuid(), tweet.getText(), tweet.getTweeter().getUsername() /* (1) */,
				tweet.getCreatedAt());
	}

	public long count() {
		return this.jdbcTemplate.queryForObject("SELECT count(*) FROM tweets",
				Long.class);
	}

	public Tweet findByUuid(UUID uuid) {
		return this.jdbcTemplate.queryForObject(
				"SELECT tw.uuid, tw.text, tw.username, tw.created_at AS tw_created_at, tr.email, tr.password, tr.created_at AS tr_created_at FROM tweets AS tw INNER JOIN tweeters AS tr ON tw.username = tr.username WHERE tw.uuid = ?",
				tweetRowMapper, uuid.toString());
	}

	public List<Tweet> findByUsername(String username) {
		return this.jdbcTemplate.query(
				"SELECT tw.uuid, tw.text, tw.username, tw.created_at AS tw_created_at, tr.email, tr.password, tr.created_at AS tr_created_at FROM tweets AS tw INNER JOIN tweeters AS tr ON tw.username = tr.username WHERE tw.username = ? ORDER BY tw.created_at DESC",
				tweetRowMapper, username);
	}

	public List<Tweet> findByUsernameSince(String username, Instant since, int limit) {
		return this.jdbcTemplate.query(
				"SELECT tw.uuid, tw.text, tw.username, tw.created_at AS tw_created_at, tr.email, tr.password, tr.created_at AS tr_created_at FROM tweets AS tw INNER JOIN tweeters AS tr ON tw.username = tr.username WHERE tw.username = ? AND tw.created_at < ? ORDER BY tw.created_at DESC LIMIT ?",
				tweetRowMapper, username, since, limit);
	}

	public List<Tweet> findByUsernameUntil(String username, Instant until, int limit) {
		return this.jdbcTemplate.query(
						"SELECT tw.uuid, tw.text, tw.username, tw.created_at AS tw_created_at, tr.email, tr.password, tr.created_at AS tr_created_at FROM tweets AS tw INNER JOIN tweeters AS tr ON tw.username = tr.username WHERE tw.username = ? AND tw.created_at > ? ORDER BY tw.created_at ASC LIMIT ?",
						tweetRowMapper, username, until, limit)
				.stream()
				.sorted(Comparator.comparing(Tweet::getCreatedAt).reversed())
				.collect(Collectors.toList());
	}

	public List<Tweet> findByTextContaining(String text) {
		return this.jdbcTemplate.query(
				"SELECT tw.uuid, tw.text, tw.username, tw.created_at AS tw_created_at, tr.email, tr.password, tr.created_at AS tr_created_at FROM tweets AS tw INNER JOIN tweeters AS tr ON tw.username = tr.username WHERE tw.text LIKE ? ORDER BY tw.created_at DESC",
				tweetRowMapper, "%" + text + "%");
	}

	public List<Tweet> findByTextContainingSince(String text, Instant since, int limit) {
		return this.jdbcTemplate.query(
				"SELECT tw.uuid, tw.text, tw.username, tw.created_at AS tw_created_at, tr.email, tr.password, tr.created_at AS tr_created_at FROM tweets AS tw INNER JOIN tweeters AS tr ON tw.username = tr.username WHERE tw.text LIKE ? AND tw.created_at < ? ORDER BY tw.created_at DESC LIMIT ?",
				tweetRowMapper, "%" + text + "%", since, limit);
	}

	public List<Tweet> findByTextContainingUntil(String text, Instant until, int limit) {
		return this.jdbcTemplate.query(
						"SELECT tw.uuid, tw.text, tw.username, tw.created_at AS tw_created_at, tr.email, tr.password, tr.created_at AS tr_created_at FROM tweets AS tw INNER JOIN tweeters AS tr ON tw.username = tr.username WHERE tw.text LIKE ? AND tw.created_at > ? ORDER BY tw.created_at ASC LIMIT ?",
						tweetRowMapper, "%" + text + "%", until, limit)
				.stream()
				.sorted(Comparator.comparing(Tweet::getCreatedAt).reversed())
				.collect(Collectors.toList());
	}

	public List<Tweet> findAll() {
		return this.jdbcTemplate.query(
				"SELECT tw.uuid, tw.text, tw.username, tw.created_at AS tw_created_at, tr.email, tr.password, tr.created_at AS tr_created_at FROM tweets AS tw INNER JOIN tweeters AS tr ON tw.username = tr.username",
				tweetRowMapper);
	}

	@Transactional
	public int deleteByUuid(UUID uuid) {
		return this.jdbcTemplate.update("DELETE FROM tweets WHERE uuid = ?", uuid.toString());
	}
}