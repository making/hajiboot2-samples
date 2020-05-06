package hajiboot;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL) /* (1) */
public class TweetMapperTest {
	private final TweetMapper tweetMapper;

	public TweetMapperTest(TweetMapper tweetMapper /* (1) */) {
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
}