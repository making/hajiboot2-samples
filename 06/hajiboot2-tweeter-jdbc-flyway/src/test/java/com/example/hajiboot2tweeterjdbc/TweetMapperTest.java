package com.example.hajiboot2tweeterjdbc;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JdbcTest
@Transactional
public class TweetMapperTest {
	@Autowired
	TweetMapper tweetMapper;

	@Test
	public void insertAndCount() {
		int updated = tweetMapper
				.insert(new Tweet(UUID.randomUUID(), "test", "foo", Instant.now()));
		assertThat(updated).isEqualTo(1);
		long count = tweetMapper.count();
		assertThat(count).isEqualTo(1);
	}

	@Test
	public void insertAndFindAll() {
		Tweet tweet1 = new Tweet(UUID.randomUUID(), "test", "foo", Instant.now());
		Tweet tweet2 = new Tweet(UUID.randomUUID(), "test", "foo", Instant.now());

		int updated1 = tweetMapper.insert(tweet1);
		assertThat(updated1).isEqualTo(1);
		int updated2 = tweetMapper.insert(tweet2);
		assertThat(updated2).isEqualTo(1);

		List<Tweet> tweets = tweetMapper.findAll();
		assertThat(tweets).containsExactly(tweet1, tweet2);
	}

	static class Config {
		@Bean
		public TweetMapper tweetMapper(JdbcTemplate jdbcTemplate) {
			return new TweetMapper(jdbcTemplate);
		}
	}
}