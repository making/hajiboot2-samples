package hajiboot;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class TweetRepositoryTest {
	private final TweetRepository tweetRepository;

	public TweetRepositoryTest(TweetRepository tweetRepository) {
		this.tweetRepository = tweetRepository;
	}

	@Test
	void insertAndCount() {
		tweetRepository.save(new Tweet(UUID.randomUUID(), "test", "foo", Instant.now()));
		long count = tweetRepository.count();
		assertThat(count).isEqualTo(1);
	}

	@Test
	void insertAndFindAll() {
		Tweet tweet1 = new Tweet(UUID.randomUUID(), "test", "foo", Instant.now());
		Tweet tweet2 = new Tweet(UUID.randomUUID(), "test", "foo", Instant.now());

		tweetRepository.save(tweet1);
		tweetRepository.save(tweet2);

		Iterable<Tweet> tweets = tweetRepository.findAll();
		assertThat(tweets).containsExactly(tweet1, tweet2);
	}
}