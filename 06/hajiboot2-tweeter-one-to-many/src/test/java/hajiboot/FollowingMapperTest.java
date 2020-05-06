package hajiboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Sql(scripts = {
        "classpath:/delete-test-following.sql",
        "classpath:/delete-test-tweeter.sql",
        "classpath:/insert-test-tweeter.sql",
        "classpath:/insert-test-following.sql"})
public class FollowingMapperTest {
    private final FollowingMapper followingMapper;

    public FollowingMapperTest(JdbcTemplate jdbcTemplate) {
        this.followingMapper = new FollowingMapper(jdbcTemplate);
    }

    @Test
    void findFollowingsByUsername() {
        final Followings followings = this.followingMapper.findFollowingsByUsername("foo");
        assertThat(followings.getUsername()).isEqualTo("foo");
        assertThat(followings.getTweeters()).containsExactly(new Tweeter("foo4"), new Tweeter("foo3"), new Tweeter("foo2"));
    }
}