package com.example.hajiboot2tweeterjpa;

import java.time.Instant;
import java.util.UUID;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TweeterApplicationRunner implements ApplicationRunner {
	private final TweetRepository tweetRepository;

	public TweeterApplicationRunner(TweetRepository tweetRepository) {
		this.tweetRepository = tweetRepository;
	}

	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		this.tweetRepository
				.save(new Tweet(UUID.randomUUID(), "Demo1", "making", Instant.now()));
		this.tweetRepository
				.save(new Tweet(UUID.randomUUID(), "Demo2", "making", Instant.now()));
		long count = this.tweetRepository.count();
		System.out.println("Number of tweets: " + count);
		Iterable<Tweet> tweets = this.tweetRepository.findAll();
		tweets.forEach(tweet -> System.out.println(tweet));
	}
}
