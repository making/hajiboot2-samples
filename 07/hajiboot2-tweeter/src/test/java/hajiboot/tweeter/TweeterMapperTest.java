package hajiboot.tweeter;

import java.time.Instant;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class TweeterMapperTest {
	private final TweeterMapper tweeterMapper;

	public TweeterMapperTest(TweeterMapper tweeterMapper) {
		this.tweeterMapper = tweeterMapper;
	}

	@Test
	void insertAndCountByUsername() {
		int updated = this.tweeterMapper.insert(new Tweeter("foo", "foo@example.com", "{noop}password", Instant.now()));
		assertThat(updated).isEqualTo(1);
		long count = this.tweeterMapper.countByUsername("foo");
		assertThat(count).isEqualTo(1);
	}

	@Test
	void insertAndFindByUsername() {
		final Instant createdAt = Instant.now();
		int updated = this.tweeterMapper.insert(new Tweeter("foo", "foo@example.com", "{noop}password", createdAt));
		assertThat(updated).isEqualTo(1);
		Tweeter tweeter = this.tweeterMapper.findByUsername("foo");
		assertThat(tweeter.getUsername()).isEqualTo("foo");
		assertThat(tweeter.getEmail()).isEqualTo("foo@example.com");
		assertThat(tweeter.getPassword()).isEqualTo("{noop}password");
		assertThat(tweeter.getCreatedAt()).isEqualTo(createdAt);
	}

	@TestConfiguration
	static class Config {
		@Bean
		public TweeterMapper tweeterMapper(JdbcTemplate jdbcTemplate) {
			return new TweeterMapper(jdbcTemplate);
		}
	}
}