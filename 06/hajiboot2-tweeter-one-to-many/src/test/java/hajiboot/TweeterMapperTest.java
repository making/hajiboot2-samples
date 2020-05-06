package hajiboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
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
}