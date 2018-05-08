package com.example.hajiboot2tweeterjpa;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class TweetMapperTest {
	@Autowired
	TweetMapper tweetMapper;

	@Test
	public void insertAndCount() {
		tweetMapper.insert(new Tweet(UUID.randomUUID(), "test", "foo", Instant.now()));
		long count = tweetMapper.count();
		assertThat(count).isEqualTo(1);
	}

	@Test
	public void insertAndFindAll() {
		Tweet tweet1 = new Tweet(UUID.randomUUID(), "test", "foo", Instant.now());
		Tweet tweet2 = new Tweet(UUID.randomUUID(), "test", "foo", Instant.now());

		tweetMapper.insert(tweet1);
		tweetMapper.insert(tweet2);

		List<Tweet> tweets = tweetMapper.findAll();
		assertThat(tweets).containsExactly(tweet1, tweet2);
	}

	static class Config {
		@Bean
		public TweetMapper tweetMapper(EntityManager entityManager) {
			return new TweetMapper(entityManager);
		}
	}
}