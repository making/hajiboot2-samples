package hajiboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Sql(scripts = {
        "classpath:/delete-test-following.sql",
        "classpath:/delete-test-tweeter.sql",
        "classpath:/insert-test-tweeter.sql",
        "classpath:/insert-test-following.sql"})
public class TweeterMapperTest {
    private final TweeterMapper tweeterMapper;

    public TweeterMapperTest(JdbcTemplate jdbcTemplate) {
        this.tweeterMapper = new TweeterMapper(jdbcTemplate);
    }

    @Test
    void insertAndCountByUsername() {
        int updated = this.tweeterMapper.insert(new Tweeter("foo", "foo@example.com", "password", Instant.now()));
        assertThat(updated).isEqualTo(1);
        long count = this.tweeterMapper.countByUsername("foo");
        assertThat(count).isEqualTo(1);
    }

    @Test
    void findFollowingsByUsername() {
        final Followings followings = this.tweeterMapper.findFollowingsByUsername("foo1");
        assertThat(followings.getUsername()).isEqualTo("foo1");
        assertThat(followings.getTweeters()).containsExactly(new Tweeter("foo4"), new Tweeter("foo3"), new Tweeter("foo2"));
    }
}