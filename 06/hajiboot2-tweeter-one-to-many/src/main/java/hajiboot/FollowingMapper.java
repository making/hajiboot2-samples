package hajiboot;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FollowingMapper {
    private final JdbcTemplate jdbcTemplate;

    public FollowingMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Followings findFollowingsByUsername(String username) {
        return this.jdbcTemplate.query("SELECT t.username, f.followee AS tweeter FROM tweeters AS t INNER JOIN followings AS f ON t.username = f.follower WHERE t.username = ? ORDER BY f.created_at DESC", new ResultSetExtractor<Followings>() {
            @Override
            public Followings extractData(ResultSet rs) throws SQLException, DataAccessException {
                String username = null;
                final List<Tweeter> tweeters = new ArrayList<>();
                while (rs.next()) {
                    if (username == null) {
                        username = rs.getString("username");
                    }
                    tweeters.add(new Tweeter(rs.getString("tweeter")));
                }
                return new Followings(username, tweeters);
            }
        }, username);
    }
}
