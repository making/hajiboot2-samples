package com.example.hajiboot2tweeterjpa;

import java.time.Instant;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class TweetRepositoryTest {
	@Autowired
	TweetRepository tweetRepository;

	@Test
	public void insertAndCount() {
		tweetRepository.save(new Tweet(UUID.randomUUID(), "test", "foo", Instant.now()));
		long count = tweetRepository.count();
		assertThat(count).isEqualTo(1);
	}

	@Test
	public void insertAndFindAll() {
		Tweet tweet1 = new Tweet(UUID.randomUUID(), "test", "foo", Instant.now());
		Tweet tweet2 = new Tweet(UUID.randomUUID(), "test", "foo", Instant.now());

		tweetRepository.save(tweet1);
		tweetRepository.save(tweet2);

		Iterable<Tweet> tweets = tweetRepository.findAll();
		assertThat(tweets).containsExactly(tweet1, tweet2);
	}
}