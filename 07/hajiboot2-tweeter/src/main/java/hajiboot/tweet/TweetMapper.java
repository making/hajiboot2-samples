package hajiboot.tweet;

import hajiboot.tweeter.Tweeter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public class TweetMapper {
    private final JdbcTemplate jdbcTemplate;

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

    public List<Tweet> findAll() {
        return this.jdbcTemplate.query(
                /* (2) */
                "SELECT tw.uuid, tw.text, tw.username, tw.created_at AS tw_created_at, tr.email, tr.password, tr.created_at AS tr_created_at FROM tweets AS tw INNER JOIN tweeters AS tr ON tw.username = tr.username",
                (rs, i) -> {
                    Tweeter tweeter = new Tweeter(rs.getString("username"),
                            rs.getString("email"), rs.getString("password"),
                            rs.getTimestamp("tr_created_at").toInstant()); // (3)
                    return new Tweet(UUID.fromString(rs.getString("uuid")),
                            rs.getString("text"), tweeter,
                            rs.getTimestamp("tw_created_at").toInstant());
                });
    }
}