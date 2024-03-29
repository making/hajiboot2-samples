package hajiboot.tweet;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import hajiboot.tweeter.Tweeter;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Sql(scripts = { "classpath:/delete-test-tweeter.sql",
		"classpath:/insert-test-tweeter.sql" }) // (1)
public class TweetMapperTest {
	private final TweetMapper tweetMapper;

	public TweetMapperTest(TweetMapper tweetMapper) {
		this.tweetMapper = tweetMapper;
	}

	@Test
	void insertAndCount() {
		int updated = tweetMapper.insert(
				new Tweet(UUID.randomUUID(), "test", new Tweeter("foo"), Instant.now()));
		assertThat(updated).isEqualTo(1);
		long count = tweetMapper.count();
		assertThat(count).isEqualTo(1);
	}

	@Test
	void insertAndFindAll() {
		Tweet tweet1 = new Tweet(UUID.randomUUID(), "test", new Tweeter("foo"),
				Instant.now());
		Tweet tweet2 = new Tweet(UUID.randomUUID(), "test", new Tweeter("foo"),
				Instant.now());

		int updated1 = tweetMapper.insert(tweet1);
		assertThat(updated1).isEqualTo(1);
		int updated2 = tweetMapper.insert(tweet2);
		assertThat(updated2).isEqualTo(1);

		List<Tweet> tweets = tweetMapper.findAll();
		assertThat(tweets).containsExactly(tweet1, tweet2);
	}

	@Test
	void insertAndFindById() {
		UUID uuid = UUID.randomUUID();
		Instant createdAt = Instant.now();
		int updated = tweetMapper.insert(
				new Tweet(uuid, "test", new Tweeter("foo"), createdAt));
		assertThat(updated).isEqualTo(1);
		Tweet tweet = tweetMapper.findByUuid(uuid);
		assertThat(tweet.getUuid()).isEqualTo(uuid);
		assertThat(tweet.getText()).isEqualTo("test");
		assertThat(tweet.getTweeter().getUsername()).isEqualTo("foo");
		assertThat(tweet.getTweeter().getEmail()).isEqualTo("foo@example.com");
		assertThat(tweet.getTweeter().getPassword()).isEqualTo("{noop}password");
		assertThat(tweet.getTweeter().getCreatedAt()).isNotNull();
		assertThat(tweet.getCreatedAt()).isEqualTo(createdAt);
	}

	@Test
	void insertAndFindByUsername() {
		Tweet tweet1 = new Tweet(UUID.randomUUID(), "test1", new Tweeter("foo"),
				Instant.now());
		Tweet tweet2 = new Tweet(UUID.randomUUID(), "test2", new Tweeter("foo2"),
				Instant.now());
		Tweet tweet3 = new Tweet(UUID.randomUUID(), "test3", new Tweeter("foo"),
				Instant.now());

		int updated1 = tweetMapper.insert(tweet1);
		assertThat(updated1).isEqualTo(1);
		int updated2 = tweetMapper.insert(tweet2);
		assertThat(updated2).isEqualTo(1);
		int updated3 = tweetMapper.insert(tweet3);
		assertThat(updated3).isEqualTo(1);

		List<Tweet> tweets = tweetMapper.findByUsername("foo");
		assertThat(tweets).containsExactly(tweet3, tweet1);
	}

	@Test
	void insertAndFindByUsernameSince() {
		Instant now = Instant.now();
		Tweet tweet1 = new Tweet(UUID.randomUUID(), "test1", new Tweeter("foo"), now.minusSeconds(6));
		Tweet tweet2 = new Tweet(UUID.randomUUID(), "test2", new Tweeter("foo2"), now.minusSeconds(5));
		Tweet tweet3 = new Tweet(UUID.randomUUID(), "test3", new Tweeter("foo"), now.minusSeconds(4));
		Tweet tweet4 = new Tweet(UUID.randomUUID(), "test4", new Tweeter("foo"), now.minusSeconds(3));
		Tweet tweet5 = new Tweet(UUID.randomUUID(), "test5", new Tweeter("foo"), now.minusSeconds(2));
		Tweet tweet6 = new Tweet(UUID.randomUUID(), "test6", new Tweeter("foo"), now.minusSeconds(1));

		int updated1 = tweetMapper.insert(tweet1);
		assertThat(updated1).isEqualTo(1);
		int updated2 = tweetMapper.insert(tweet2);
		assertThat(updated2).isEqualTo(1);
		int updated3 = tweetMapper.insert(tweet3);
		assertThat(updated3).isEqualTo(1);
		int updated4 = tweetMapper.insert(tweet4);
		assertThat(updated4).isEqualTo(1);
		int updated5 = tweetMapper.insert(tweet5);
		assertThat(updated5).isEqualTo(1);
		int updated6 = tweetMapper.insert(tweet6);
		assertThat(updated6).isEqualTo(1);

		List<Tweet> tweetsPage1 = tweetMapper.findByUsernameSince("foo", now, 3);
		assertThat(tweetsPage1).containsExactly(tweet6, tweet5, tweet4);
		List<Tweet> tweetsPage2 = tweetMapper.findByUsernameSince("foo", tweet4.getCreatedAt(), 3);
		assertThat(tweetsPage2).containsExactly(tweet3, tweet1);
	}

	@Test
	void insertAndFindByUsernameUntil() {
		Instant now = Instant.now();
		Tweet tweet1 = new Tweet(UUID.randomUUID(), "test1", new Tweeter("foo"), now.minusSeconds(6));
		Tweet tweet2 = new Tweet(UUID.randomUUID(), "test2", new Tweeter("foo2"), now.minusSeconds(5));
		Tweet tweet3 = new Tweet(UUID.randomUUID(), "test3", new Tweeter("foo"), now.minusSeconds(4));
		Tweet tweet4 = new Tweet(UUID.randomUUID(), "test4", new Tweeter("foo"), now.minusSeconds(3));
		Tweet tweet5 = new Tweet(UUID.randomUUID(), "test5", new Tweeter("foo"), now.minusSeconds(2));
		Tweet tweet6 = new Tweet(UUID.randomUUID(), "test6", new Tweeter("foo"), now.minusSeconds(1));

		int updated1 = tweetMapper.insert(tweet1);
		assertThat(updated1).isEqualTo(1);
		int updated2 = tweetMapper.insert(tweet2);
		assertThat(updated2).isEqualTo(1);
		int updated3 = tweetMapper.insert(tweet3);
		assertThat(updated3).isEqualTo(1);
		int updated4 = tweetMapper.insert(tweet4);
		assertThat(updated4).isEqualTo(1);
		int updated5 = tweetMapper.insert(tweet5);
		assertThat(updated5).isEqualTo(1);
		int updated6 = tweetMapper.insert(tweet6);
		assertThat(updated6).isEqualTo(1);

		List<Tweet> tweetsPage1 = tweetMapper.findByUsernameUntil("foo", now.minusSeconds(7), 3);
		assertThat(tweetsPage1).containsExactly(tweet4, tweet3, tweet1);
		List<Tweet> tweetsPage2 = tweetMapper.findByUsernameUntil("foo", tweet4.getCreatedAt(), 3);
		assertThat(tweetsPage2).containsExactly(tweet6, tweet5);
	}

	@Test
	void insertAndFindByTextContaining() {
		Tweet tweet1 = new Tweet(UUID.randomUUID(), "foo1", new Tweeter("foo"),
				Instant.now());
		Tweet tweet2 = new Tweet(UUID.randomUUID(), "1bar", new Tweeter("foo2"),
				Instant.now());
		Tweet tweet3 = new Tweet(UUID.randomUUID(), "bar3", new Tweeter("foo"),
				Instant.now());

		int updated1 = tweetMapper.insert(tweet1);
		assertThat(updated1).isEqualTo(1);
		int updated2 = tweetMapper.insert(tweet2);
		assertThat(updated2).isEqualTo(1);
		int updated3 = tweetMapper.insert(tweet3);
		assertThat(updated3).isEqualTo(1);

		List<Tweet> tweets = tweetMapper.findByTextContaining("bar");
		assertThat(tweets).containsExactly(tweet3, tweet2);
	}

	@Test
	void insertAndFindByTextContainingSince() {
		Instant now = Instant.now();
		Tweet tweet1 = new Tweet(UUID.randomUUID(), "foo1", new Tweeter("foo"), now.minusSeconds(6));
		Tweet tweet2 = new Tweet(UUID.randomUUID(), "1bar", new Tweeter("foo2"), now.minusSeconds(5));
		Tweet tweet3 = new Tweet(UUID.randomUUID(), "bar3", new Tweeter("foo"), now.minusSeconds(4));
		Tweet tweet4 = new Tweet(UUID.randomUUID(), "bar4", new Tweeter("foo"), now.minusSeconds(3));
		Tweet tweet5 = new Tweet(UUID.randomUUID(), "bar5", new Tweeter("foo"), now.minusSeconds(2));
		Tweet tweet6 = new Tweet(UUID.randomUUID(), "bar6", new Tweeter("foo"), now.minusSeconds(1));

		int updated1 = tweetMapper.insert(tweet1);
		assertThat(updated1).isEqualTo(1);
		int updated2 = tweetMapper.insert(tweet2);
		assertThat(updated2).isEqualTo(1);
		int updated3 = tweetMapper.insert(tweet3);
		assertThat(updated3).isEqualTo(1);
		int updated4 = tweetMapper.insert(tweet4);
		assertThat(updated4).isEqualTo(1);
		int updated5 = tweetMapper.insert(tweet5);
		assertThat(updated5).isEqualTo(1);
		int updated6 = tweetMapper.insert(tweet6);
		assertThat(updated6).isEqualTo(1);

		List<Tweet> tweetsPage1 = tweetMapper.findByTextContainingSince("bar", now, 3);
		assertThat(tweetsPage1).containsExactly(tweet6, tweet5, tweet4);
		List<Tweet> tweetsPage2 = tweetMapper.findByTextContainingSince("bar", tweet4.getCreatedAt(), 3);
		assertThat(tweetsPage2).containsExactly(tweet3, tweet2);
	}

	@Test
	void insertAndFindByTextContainingUntil() {
		Instant now = Instant.now();
		Tweet tweet1 = new Tweet(UUID.randomUUID(), "foo1", new Tweeter("foo"), now.minusSeconds(6));
		Tweet tweet2 = new Tweet(UUID.randomUUID(), "1bar", new Tweeter("foo2"), now.minusSeconds(5));
		Tweet tweet3 = new Tweet(UUID.randomUUID(), "bar3", new Tweeter("foo"), now.minusSeconds(4));
		Tweet tweet4 = new Tweet(UUID.randomUUID(), "bar4", new Tweeter("foo"), now.minusSeconds(3));
		Tweet tweet5 = new Tweet(UUID.randomUUID(), "bar5", new Tweeter("foo"), now.minusSeconds(2));
		Tweet tweet6 = new Tweet(UUID.randomUUID(), "bar6", new Tweeter("foo"), now.minusSeconds(1));

		int updated1 = tweetMapper.insert(tweet1);
		assertThat(updated1).isEqualTo(1);
		int updated2 = tweetMapper.insert(tweet2);
		assertThat(updated2).isEqualTo(1);
		int updated3 = tweetMapper.insert(tweet3);
		assertThat(updated3).isEqualTo(1);
		int updated4 = tweetMapper.insert(tweet4);
		assertThat(updated4).isEqualTo(1);
		int updated5 = tweetMapper.insert(tweet5);
		assertThat(updated5).isEqualTo(1);
		int updated6 = tweetMapper.insert(tweet6);
		assertThat(updated6).isEqualTo(1);

		List<Tweet> tweetsPage1 = tweetMapper.findByTextContainingUntil("bar", now.minusSeconds(7), 3);
		assertThat(tweetsPage1).containsExactly(tweet4, tweet3, tweet2);
		List<Tweet> tweetsPage2 = tweetMapper.findByTextContainingUntil("bar", tweet4.getCreatedAt(), 3);
		assertThat(tweetsPage2).containsExactly(tweet6, tweet5);
	}

	@Test
	void insertAndDeleteAndCount() {
		UUID uuid = UUID.randomUUID();
		int updated = tweetMapper.insert(
				new Tweet(uuid, "test", new Tweeter("foo"), Instant.now()));
		assertThat(updated).isEqualTo(1);
		int delete = tweetMapper.deleteByUuid(uuid);
		assertThat(delete).isEqualTo(1);
		long count = tweetMapper.count();
		assertThat(count).isEqualTo(0);
	}

	@TestConfiguration
	static class Config {
		@Bean
		public TweetMapper tweetMapper(JdbcTemplate jdbcTemplate) {
			return new TweetMapper(jdbcTemplate);
		}
	}
}