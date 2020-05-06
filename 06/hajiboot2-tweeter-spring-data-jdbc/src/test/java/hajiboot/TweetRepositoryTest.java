package hajiboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestConstructor;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest // (1)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class TweetRepositoryTest {
	private final TweetRepository tweetRepository;

	public TweetRepositoryTest(TweetRepository tweetRepository /* (2) */) {
		this.tweetRepository = tweetRepository;
	}

	@Test
	void insertAndCount() {
		tweetRepository.save(new Tweet(null /* (3) */, "test", "foo", Instant.now()));
		long count = tweetRepository.count();
		assertThat(count).isEqualTo(1);
	}

	@Test
	void insertAndFindAll() {
		Tweet tweet1 = new Tweet(null /* (3) */, "test", "foo", Instant.now());
		Tweet tweet2 = new Tweet(null /* (3) */, "test", "foo", Instant.now());

		tweetRepository.save(tweet1);
		tweetRepository.save(tweet2);

		Iterable<Tweet> tweets = tweetRepository.findAll();
		assertThat(tweets).containsExactly(tweet1, tweet2);
		assertThat(tweet1.getUuid()).isNotNull(); // (4)
		assertThat(tweet1.getUuid().toString()).containsPattern("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"); // (4)
		assertThat(tweet2.getUuid()).isNotNull(); // (4)
		assertThat(tweet2.getUuid().toString()).containsPattern("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"); // (4)
	}

	@TestConfiguration // (5)
	static class Config {
		@Bean
		public TweetEventListener tweetEventListenerForTest() {
			return new TweetEventListener();
		}
	}
}