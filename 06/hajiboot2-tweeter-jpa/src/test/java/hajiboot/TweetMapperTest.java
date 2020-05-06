package hajiboot;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class TweetMapperTest {
	private final TweetMapper tweetMapper;

	public TweetMapperTest(EntityManager entityManager) {
		this.tweetMapper = new TweetMapper(entityManager);
	}

	@Test
	void insertAndCount() {
		tweetMapper.insert(new Tweet(UUID.randomUUID(), "test", "foo", Instant.now()));
		long count = tweetMapper.count();
		assertThat(count).isEqualTo(1);
	}

	@Test
	void insertAndFindAll() {
		Tweet tweet1 = new Tweet(UUID.randomUUID(), "test", "foo", Instant.now());
		Tweet tweet2 = new Tweet(UUID.randomUUID(), "test", "foo", Instant.now());

		tweetMapper.insert(tweet1);
		tweetMapper.insert(tweet2);

		List<Tweet> tweets = tweetMapper.findAll();
		Assertions.assertThat(tweets).containsExactly(tweet1, tweet2);
	}
}