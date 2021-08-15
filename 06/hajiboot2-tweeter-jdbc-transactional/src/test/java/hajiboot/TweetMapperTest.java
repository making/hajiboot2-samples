package hajiboot;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest // (1)
@Transactional // (2)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL) /* (3) */
public class TweetMapperTest {
	private final TweetMapper tweetMapper;

	public TweetMapperTest(TweetMapper tweetMapper) {
		this.tweetMapper = tweetMapper;
	}

	@Test
	void insertAndCount() {
		int updated = tweetMapper
				.insert(new Tweet(UUID.randomUUID(), "test", "foo", Instant.now()));
		assertThat(updated).isEqualTo(1);
		long count = tweetMapper.count();
		assertThat(count).isEqualTo(1);
	}

	@Test
	void insertAndFindAll() {
		Tweet tweet1 = new Tweet(UUID.randomUUID(), "test", "foo", Instant.now());
		Tweet tweet2 = new Tweet(UUID.randomUUID(), "test", "foo", Instant.now());

		int updated1 = tweetMapper.insert(tweet1);
		assertThat(updated1).isEqualTo(1);
		int updated2 = tweetMapper.insert(tweet2);
		assertThat(updated2).isEqualTo(1);

		List<Tweet> tweets = tweetMapper.findAll();
		assertThat(tweets).containsExactly(tweet1, tweet2);
	}

	@TestConfiguration
	static class Config {
		@Bean
		public TweetMapper tweetMapper(JdbcTemplate jdbcTemplate) {
			return new TweetMapper(jdbcTemplate);
		}
	}
}