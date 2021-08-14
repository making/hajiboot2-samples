package hajiboot.followings;

import java.util.ArrayList;
import java.util.List;

import hajiboot.tweeter.Tweeter;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class FollowingsMapper {
	private final JdbcTemplate jdbcTemplate;

	private final ResultSetExtractor<Followings> followingsResultSetExtractor = rs -> {
		String username = null;
		final List<Tweeter> tweeters = new ArrayList<>();
		while (rs.next()) {
			if (username == null) {
				username = rs.getString("username");
			}
			tweeters.add(new Tweeter(rs.getString("tweeter")));
		}
		return new Followings(username, tweeters);
	};

	public FollowingsMapper(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Followings findFollowingsByUsername(String follower) {
		return this.jdbcTemplate.query("SELECT t.username, f.followee AS tweeter FROM tweeters AS t INNER JOIN followings AS f ON t.username = f.follower WHERE t.username = ? ORDER BY f.created_at DESC",
				followingsResultSetExtractor, follower);
	}

	public Followings findFollowersByUsername(String followee) {
		return this.jdbcTemplate.query("SELECT t.username, f.follower AS tweeter FROM tweeters AS t INNER JOIN followings AS f ON t.username = f.followee WHERE t.username = ? ORDER BY f.created_at DESC",
				followingsResultSetExtractor, followee);
	}

	@Transactional
	public int insert(String follower, String followee) {
		return this.jdbcTemplate.update("INSERT INTO followings(followee, follower) VALUES (?, ?)", followee, follower);
	}

	@Transactional
	public int delete(String follower, String followee) {
		return this.jdbcTemplate.update("DELETE FROM followings WHERE followee = ? AND follower = ?", followee, follower);
	}
}
