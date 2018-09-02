package com.example.hajiboot2tweeterspringdatajdbc;

import java.time.Instant;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jdbc.repository.config.JdbcConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JdbcTest
@Transactional
@EnableJdbcRepositories
public class TweetRepositoryTest {
	@Autowired
	TweetRepository tweetRepository;

	@Test
	public void insertAndCount() {
		tweetRepository.save(new Tweet(null, "test", "foo", Instant.now()));
		long count = tweetRepository.count();
		assertThat(count).isEqualTo(1);
	}

	@Test
	public void insertAndFindAll() {
		Tweet tweet1 = new Tweet(null, "test", "foo", Instant.now());
		Tweet tweet2 = new Tweet(null, "test", "foo", Instant.now());

		tweetRepository.save(tweet1);
		tweetRepository.save(tweet2);

		Iterable<Tweet> tweets = tweetRepository.findAll();
		assertThat(tweets).containsExactly(tweet1, tweet2);
	}

	@Configuration
	@Import(JdbcConfiguration.class)
	static class Config {
		@Bean
		public TweetEventListener tweetEventListener() {
			return new TweetEventListener();
		}
	}
}